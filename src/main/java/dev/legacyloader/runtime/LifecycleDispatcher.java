package dev.legacyloader.runtime;

import dev.legacyloader.bridge.LegacyModContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Reflective bridge between the emulated lifecycle phase and the legacy mod's
 * {@code @Mod.EventHandler} methods. All types it touches live in the legacy
 * loader, except {@link LegacyModContext}, which is deliberately shared.
 */
final class LifecycleDispatcher {
    private LifecycleDispatcher() {
    }

    static List<Method> findHandlers(Class<?> modClass, String annotationClassName) {
        List<Method> result = new ArrayList<>();
        Class<?> current = modClass;
        while (current != null && current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (hasAnnotation(method, annotationClassName)) {
                    result.add(method);
                }
            }
            current = current.getSuperclass();
        }
        result.sort(Comparator.comparing(Method::getName));
        return result;
    }

    private static boolean hasAnnotation(Method method, String annotationClassName) {
        try {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().getName().equals(annotationClassName)) {
                    return true;
                }
            }
        } catch (RuntimeException | LinkageError ignored) {
            // an unresolvable annotation type is not a lifecycle handler
        }
        return false;
    }

    static boolean accepts(Method method, String eventClassName) {
        if (method.getParameterCount() != 1) {
            return false;
        }
        return method.getParameterTypes()[0].getName().equals(eventClassName);
    }

    static Object createEvent(String eventClassName, ClassLoader loader, LegacyModContext context) {
        try {
            Class<?> eventClass = Class.forName(eventClassName, true, loader);
            try {
                Constructor<?> constructor = eventClass.getDeclaredConstructor(LegacyModContext.class);
                constructor.setAccessible(true);
                return constructor.newInstance(context);
            } catch (NoSuchMethodException ignored) {
                Constructor<?> constructor = eventClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create lifecycle event " + eventClassName, e);
        }
    }
}
