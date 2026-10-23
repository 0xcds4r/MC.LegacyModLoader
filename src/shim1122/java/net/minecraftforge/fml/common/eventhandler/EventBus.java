package net.minecraftforge.fml.common.eventhandler;

import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacySide;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Minimal but functional 1.12.2 event bus.
 *
 * <p>Listeners can be objects (instance {@code @SubscribeEvent} methods) or
 * classes registered through {@link #registerClass(Class)} (static handlers used
 * by {@code @Mod.EventBusSubscriber}). Dispatch matches the erased parameter
 * type, applies priority order and isolates handler failures.
 */
public class EventBus {
    private static final Logger LOG = LogManager.getLogger("legacy/eventbus");
    private static final String SUBSCRIBE_DESCRIPTOR = "net.minecraftforge.fml.common.eventhandler.SubscribeEvent";

    private final List<Object> listeners = new CopyOnWriteArrayList<>();
    private final Map<Object, String> owners = new ConcurrentHashMap<>();

    public void register(Object target) {
        if (target == null || listeners.contains(target)) {
            return;
        }
        listeners.add(target);
        String owner = currentModId();
        if (owner != null) {
            owners.put(target, owner);
        }
        try {
            BridgeHolder.require().registerEventListener(target);
        } catch (IllegalStateException ignored) {
            // bus used before the legacy runtime installed its bridge
        }
    }

    public void registerClass(Class<?> type) {
        register((Object) type);
    }

    public void unregister(Object target) {
        listeners.remove(target);
        try {
            BridgeHolder.require().unregisterEventListener(target);
        } catch (IllegalStateException ignored) {
            // bridge already gone during shutdown
        }
    }

    public boolean post(Event event) {
        return dispatch(event, null);
    }

    /**
     * Dispatches only handlers registered by the given mod (plus owner-less
     * handlers). Registry events use this so one mod's registration callbacks
     * cannot register content under another mod's namespace.
     */
    public boolean postForMod(Event event, String modId) {
        return dispatch(event, modId);
    }

    private boolean dispatch(Event event, String modId) {
        if (event == null) {
            return false;
        }
        List<Invocation> invocations = new ArrayList<>();
        for (Object listener : listeners) {
            if (modId != null && !modId.equals(owners.get(listener))) {
                continue;
            }
            Class<?> type = listener instanceof Class<?> clazz ? clazz : listener.getClass();
            Object instance = listener instanceof Class<?> ? null : listener;
            collect(type, instance, event, invocations);
        }
        invocations.sort(Comparator.comparingInt(invocation -> invocation.priority));

        for (Invocation invocation : invocations) {
            if (!invocation.method.canAccess(invocation.target)) {
                invocation.method.setAccessible(true);
            }
            try {
                invocation.method.invoke(invocation.target, event);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause() == null ? e : e.getCause();
                LOG.error("Legacy event handler {} failed for {}",
                        invocation.method, event.getClass().getName(), cause);
            } catch (ReflectiveOperationException | RuntimeException e) {
                LOG.error("Cannot invoke legacy event handler {}", invocation.method, e);
            }
        }
        return event.isCanceled();
    }

    private static void collect(Class<?> type, Object target, Event event, List<Invocation> out) {
        Class<?> current = type;
        while (current != null && current != Object.class) {
            Method[] methods;
            try {
                methods = current.getDeclaredMethods();
            } catch (RuntimeException | LinkageError t) {
                LOG.warn("Cannot inspect event handler class {}", current.getName(), t);
                current = current.getSuperclass();
                continue;
            }
            for (Method method : methods) {
                if (method.getParameterCount() != 1) {
                    continue;
                }
                Annotation subscribe = findAnnotation(method, SUBSCRIBE_DESCRIPTOR);
                if (subscribe == null) {
                    continue;
                }
                if (!method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    continue;
                }
                if (!registryMatches(method, event)) {
                    continue;
                }
                if (!sideMatches(method)) {
                    continue;
                }
                if (target == null && !Modifier.isStatic(method.getModifiers())) {
                    continue;
                }
                if (target != null && Modifier.isStatic(method.getModifiers())) {
                    continue;
                }
                out.add(new Invocation(method, target, priority(subscribe)));
            }
            current = current.getSuperclass();
        }
    }

    private static Annotation findAnnotation(Method method, String name) {
        try {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().getName().equals(name)) {
                    return annotation;
                }
            }
        } catch (RuntimeException | LinkageError ignored) {
            // unresolvable annotation type
        }
        return null;
    }

    private static boolean registryMatches(Method method, Event event) {
        if (!(event instanceof RegistryEvent.Register<?> register)) {
            return true;
        }
        Type generic = method.getGenericParameterTypes()[0];
        if (!(generic instanceof ParameterizedType parameterized)) {
            return true;
        }
        Type argument = parameterized.getActualTypeArguments()[0];
        if (!(argument instanceof Class<?> type)) {
            return true;
        }
        String expected = registryPath(type);
        return expected == null || register.getRegistry().getRegistryName().getPath().equals(expected);
    }

    private static String registryPath(Class<?> type) {
        if (type == Block.class) {
            return "blocks";
        }
        if (type == Item.class) {
            return "items";
        }
        if (type == SoundEvent.class) {
            return "sounds";
        }
        if (type == Potion.class) {
            return "potions";
        }
        if (type == Biome.class) {
            return "biomes";
        }
        if (type == EntityEntry.class) {
            return "entities";
        }
        if (type == IRecipe.class) {
            return "recipes";
        }
        return null;
    }

    private static boolean sideMatches(Method method) {
        Annotation sideOnly = findAnnotation(method,
                "net.minecraftforge.fml.relauncher.SideOnly");
        if (sideOnly == null) {
            return true;
        }
        try {
            Object value = sideOnly.annotationType().getMethod("value").invoke(sideOnly);
            LegacySide side = BridgeHolder.require().currentContext().side();
            return String.valueOf(value).equals(side.name());
        } catch (RuntimeException | ReflectiveOperationException | LinkageError ignored) {
            return true;
        }
    }

    private static int priority(Annotation subscribe) {
        try {
            Object value = subscribe.annotationType().getMethod("priority").invoke(subscribe);
            if (value instanceof EventPriority eventPriority) {
                return eventPriority.ordinal();
            }
        } catch (ReflectiveOperationException ignored) {
            // default priority
        }
        return EventPriority.NORMAL.ordinal();
    }

    public List<Object> listeners() {
        return List.copyOf(listeners);
    }

    private static String currentModId() {
        try {
            return BridgeHolder.require().currentContext().modId();
        } catch (RuntimeException | LinkageError ignored) {
            return null;
        }
    }

    private record Invocation(Method method, Object target, int priority) {
    }
}
