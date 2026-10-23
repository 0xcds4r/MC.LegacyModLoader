package dev.legacyloader.runtime;

import dev.legacyloader.api.LifecyclePhase;
import dev.legacyloader.api.LegacyProfile;
import dev.legacyloader.bridge.LegacyModContext;
import dev.legacyloader.bridge.LegacySide;
import dev.legacyloader.classloader.LegacyClassLoader;
import dev.legacyloader.classloader.ModJarSource;
import dev.legacyloader.discovery.DiscoveredMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Runtime state for one legacy mod: its classloader, instance, lifecycle
 * position and the context exposed to shims.
 */
public final class LegacyModContainer implements LegacyModContext {
    public enum State {
        DISCOVERED,
        LOADER_READY,
        CONSTRUCTED,
        PRE_INIT,
        INIT,
        POST_INIT,
        COMPLETE,
        ERRORED,
        LIBRARY
    }

    private static final Logger LOG = LoggerFactory.getLogger("legacyloader");

    private final DiscoveredMod discovered;
    private final ModJarSource source;
    private final LegacySide side;
    private final Path configDirectory;
    private final Path gameDirectory;
    private LegacyClassLoader classLoader;
    private Object instance;
    private State state = State.DISCOVERED;
    private Throwable failure;

    public LegacyModContainer(DiscoveredMod discovered,
                              ModJarSource source,
                              LegacySide side,
                              Path configDirectory,
                              Path gameDirectory) {
        this.discovered = Objects.requireNonNull(discovered);
        this.source = Objects.requireNonNull(source);
        this.side = Objects.requireNonNull(side);
        this.configDirectory = configDirectory;
        this.gameDirectory = gameDirectory;
    }

    public void attachClassLoader(LegacyClassLoader classLoader) {
        if (this.classLoader != null) {
            throw new IllegalStateException("Classloader already attached for " + modId());
        }
        this.classLoader = classLoader;
        this.state = discovered.hasEntryPoint() ? State.LOADER_READY : State.LIBRARY;
    }

    public DiscoveredMod discovered() {
        return discovered;
    }

    public LegacyClassLoader classLoader() {
        return classLoader;
    }

    public Object instance() {
        return instance;
    }

    public State state() {
        return state;
    }

    public Throwable failure() {
        return failure;
    }

    public boolean isFailed() {
        return state == State.ERRORED;
    }

    public void construct() {
        if (!discovered.hasEntryPoint()) {
            state = State.LIBRARY;
            return;
        }
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        try {
            Class<?> modClass = Class.forName(discovered.modClass(), true, classLoader);
            Constructor<?> constructor = modClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            LegacyInjector.inject(modClass, instance, this);
            state = State.CONSTRUCTED;
            LOG.info("[{}] constructed {}", modId(), discovered.modClass());
        } catch (Throwable t) {
            fail("construction", t);
        } finally {
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    public void invokePhase(LifecyclePhase phase, LegacyProfile profile) {
        if (instance == null || isFailed()) {
            return;
        }
        String eventClassName = profile.lifecycleEventClass(phase);
        if (eventClassName == null) {
            return;
        }
        List<Method> handlers = LifecycleDispatcher.findHandlers(
                instance.getClass(), profile.eventHandlerAnnotationClass());
        if (handlers.isEmpty()) {
            return;
        }

        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        try {
            Object event = LifecycleDispatcher.createEvent(eventClassName, classLoader, this);
            for (Method handler : handlers) {
                if (!LifecycleDispatcher.accepts(handler, eventClassName)) {
                    continue;
                }
                try {
                    handler.setAccessible(true);
                    handler.invoke(instance, event);
                } catch (InvocationTargetException e) {
                    fail("phase " + phase + " in " + handler.getName(), e.getCause() == null ? e : e.getCause());
                    return;
                } catch (ReflectiveOperationException | RuntimeException e) {
                    fail("phase " + phase + " in " + handler.getName(), e);
                    return;
                }
            }
            state = switch (phase) {
                case PRE_INIT -> State.PRE_INIT;
                case INIT -> State.INIT;
                case POST_INIT -> State.POST_INIT;
                case LOAD_COMPLETE -> State.COMPLETE;
                default -> state;
            };
        } catch (Throwable t) {
            fail("event creation for phase " + phase, t);
        } finally {
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    private void fail(String activity, Throwable throwable) {
        this.failure = throwable;
        this.state = State.ERRORED;
        LOG.error("[{}] failed during {}", modId(), activity, throwable);
    }

    @Override
    public String modId() {
        return discovered.modId();
    }

    @Override
    public String modName() {
        return discovered.info().name();
    }

    @Override
    public String modVersion() {
        return discovered.info().version();
    }

    @Override
    public LegacySide side() {
        return side;
    }

    @Override
    public Path configDirectory() {
        return configDirectory;
    }

    @Override
    public Path gameDirectory() {
        return gameDirectory;
    }

    @Override
    public String loggerName() {
        return "legacy/" + modId();
    }

    @Override
    public Object modInstance() {
        return instance;
    }

    @Override
    public ClassLoader modClassLoader() {
        return classLoader;
    }

    @Override
    public Path sourceJar() {
        return source.path();
    }

    public List<String> classEntries() {
        return new ArrayList<>(classLoader == null ? List.of() : classLoader.classEntries());
    }
}
