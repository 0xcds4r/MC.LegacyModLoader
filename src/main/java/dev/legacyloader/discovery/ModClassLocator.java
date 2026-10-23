package dev.legacyloader.discovery;

import dev.legacyloader.api.LegacyProfile;
import dev.legacyloader.classloader.BytecodeInspector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Finds the {@code @Mod} class in a legacy jar using ASM, without loading it.
 * Loading every class just to check annotations would run static initializers
 * and would break mods that touch the game during {@code <clinit>}.
 */
public final class ModClassLocator {
    private ModClassLocator() {
    }

    public record Result(String modClass, String annotationDependencies) {
    }

    public static Result locate(java.nio.file.Path jarPath, LegacyProfile profile, String expectedModId) {
        String annotationDescriptor = BytecodeInspector.toDescriptor(profile.modAnnotationClass());
        String firstMatch = null;
        String firstDependencies = "";
        String exactMatch = null;
        String exactDependencies = "";

        try (JarFile jar = new JarFile(jarPath.toFile(), false)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || !name.endsWith(".class") || name.startsWith("META-INF/")) {
                    continue;
                }
                if (name.startsWith("net/minecraft/") || name.startsWith("net/minecraftforge/")) {
                    // shaded game classes are not mod entry points
                    continue;
                }
                byte[] bytes;
                try (InputStream in = jar.getInputStream(entry)) {
                    bytes = in.readAllBytes();
                } catch (IOException ignored) {
                    continue;
                }
                Map<String, Object> values;
                try {
                    values = BytecodeInspector.readAnnotation(bytes, annotationDescriptor);
                } catch (RuntimeException malformed) {
                    continue;
                }
                if (values == null) {
                    continue;
                }
                String binaryName = name.substring(0, name.length() - ".class".length()).replace('/', '.');
                String modId = String.valueOf(values.getOrDefault("modid", ""));
                String dependencies = String.valueOf(values.getOrDefault("dependencies", ""));

                if (firstMatch == null) {
                    firstMatch = binaryName;
                    firstDependencies = dependencies;
                }
                if (expectedModId != null && expectedModId.equalsIgnoreCase(modId)) {
                    exactMatch = binaryName;
                    exactDependencies = dependencies;
                    break;
                }
            }
        } catch (IOException e) {
            return new Result(null, "");
        }

        if (exactMatch != null) {
            return new Result(exactMatch, exactDependencies);
        }
        return new Result(firstMatch, firstDependencies);
    }
}
