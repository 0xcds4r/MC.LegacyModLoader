package dev.legacyloader.runtime;

import dev.legacyloader.bridge.LegacySide;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Emulates Forge 1.12.2 field injection:
 * <ul>
 *   <li>{@code @Mod.Instance} -> the constructed mod object;</li>
 *   <li>{@code @SidedProxy} -> client or server proxy, loaded through the
 *       mod's own classloader;</li>
 *   <li>{@code @Mod.Metadata} -> a populated metadata shim.</li>
 * </ul>
 *
 * This happens after the constructor and before any lifecycle phase, matching
 * Forge (large mods often dereference {@code instance} in preInit).
 */
final class LegacyInjector {
    private LegacyInjector() {
    }

    static void inject(Class<?> modClass, Object instance, LegacyModContainer container) {
        for (Field field : modClass.getDeclaredFields()) {
            Annotation[] annotations;
            try {
                annotations = field.getDeclaredAnnotations();
            } catch (RuntimeException | LinkageError e) {
                continue;
            }
            for (Annotation annotation : annotations) {
                String type = annotation.annotationType().getName();
                try {
                    switch (type) {
                        case "net.minecraftforge.fml.common.Mod$Instance" ->
                                setStatic(field, instance);
                        case "net.minecraftforge.fml.common.SidedProxy" ->
                                injectProxy(field, annotation, container);
                        case "net.minecraftforge.fml.common.Mod$Metadata" ->
                                injectMetadata(field, container);
                        default -> {
                        }
                    }
                } catch (ReflectiveOperationException | RuntimeException e) {
                    throw new IllegalStateException("Failed to inject " + type + " into "
                            + modClass.getName() + "." + field.getName(), e);
                }
            }
        }
    }

    private static void injectProxy(Field field, Annotation annotation, LegacyModContainer container)
            throws ReflectiveOperationException {
        String clientSide = stringValue(annotation, "clientSide");
        String serverSide = stringValue(annotation, "serverSide");
        boolean forceServerProxy = Boolean.getBoolean("legacyloader.forceServerProxy");
        String chosen = forceServerProxy && !serverSide.isBlank()
                ? serverSide
                : (container.side() == LegacySide.CLIENT ? clientSide : serverSide);
        if (chosen == null || chosen.isBlank()) {
            chosen = clientSide;
        }
        if (chosen == null || chosen.isBlank()) {
            return;
        }
        try {
            setStatic(field, instantiateProxy(chosen, container));
        } catch (Throwable failure) {
            // Client proxies in 1.12 often bind renderers/GL state that do not
            // exist yet. Falling back to the server proxy keeps registration and
            // gameplay logic alive instead of disabling the whole mod.
            if (!serverSide.isBlank() && !serverSide.equals(chosen)) {
                org.slf4j.LoggerFactory.getLogger("legacyloader").warn(
                        "Client proxy {} failed ({}); falling back to {}",
                        chosen, failure.toString(), serverSide);
                setStatic(field, instantiateProxy(serverSide, container));
            } else {
                throw failure;
            }
        }
    }

    private static Object instantiateProxy(String className, LegacyModContainer container)
            throws ReflectiveOperationException {
        Class<?> proxyClass = Class.forName(className, true, container.classLoader());
        Constructor<?> constructor = proxyClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static void injectMetadata(Field field, LegacyModContainer container)
            throws ReflectiveOperationException {
        Class<?> metadataClass = Class.forName("net.minecraftforge.fml.common.ModMetadata",
                true, container.classLoader());
        Object metadata = metadataClass.getDeclaredConstructor().newInstance();
        setIfPresent(metadataClass, metadata, "modId", container.modId());
        setIfPresent(metadataClass, metadata, "name", container.modName());
        setIfPresent(metadataClass, metadata, "version", container.modVersion());
        setStatic(field, metadata);
    }

    private static void setIfPresent(Class<?> type, Object target, String fieldName, String value) {
        try {
            Field field = type.getField(fieldName);
            field.set(target, value);
        } catch (ReflectiveOperationException ignored) {
            // metadata shape differs between shim revisions
        }
    }

    private static String stringValue(Annotation annotation, String methodName)
            throws ReflectiveOperationException {
        Method method = annotation.annotationType().getMethod(methodName);
        Object value = method.invoke(annotation);
        return value instanceof String string ? string : "";
    }

    private static void setStatic(Field field, Object value) throws IllegalAccessException {
        if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
            return;
        }
        field.setAccessible(true);
        field.set(null, value);
    }
}
