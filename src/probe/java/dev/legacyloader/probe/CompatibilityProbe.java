package dev.legacyloader.probe;

import dev.legacyloader.classloader.ClassRules;
import dev.legacyloader.classloader.LegacyClassLoader;
import dev.legacyloader.classloader.ModJarSource;
import dev.legacyloader.classloader.PlatformGateway;
import dev.legacyloader.classloader.ShimIndex;
import dev.legacyloader.classloader.SrgMappings;
import dev.legacyloader.discovery.McmodInfo;
import dev.legacyloader.discovery.McmodInfoParser;
import dev.legacyloader.discovery.ModClassLocator;
import dev.legacyloader.profile.Forge1122Profile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarFile;

/**
 * Headless compatibility probe. Loads every class from a legacy jar through the
 * real classloader and reports the first missing type for each failure. No
 * Minecraft bootstrap is required.
 *
 * <pre>
 * gradle runCompatibilityProbe --args="/path/to/mod.jar"
 * gradle runCompatibilityProbe --args="/path/to/mod.jar --include-client"
 * </pre>
 */
public final class CompatibilityProbe {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("usage: CompatibilityProbe <legacy.jar> [entryClass] [--include-client]");
            System.exit(2);
        }

        Path jarPath = Path.of(args[0]);
        boolean includeClient = false;
        String explicitEntry = null;
        for (int i = 1; i < args.length; i++) {
            if ("--include-client".equals(args[i])) {
                includeClient = true;
            } else {
                explicitEntry = args[i];
            }
        }

        Forge1122Profile profile = new Forge1122Profile();
        ShimIndex shims = ShimIndex.load(profile);
        ClassRules rules = new ClassRules(shims);
        PlatformGateway gateway = new PlatformGateway(CompatibilityProbe.class.getClassLoader(), rules);
        SrgMappings mappings = SrgMappings.load(profile.srgMappingsDirectory());

        String modId;
        String entryClass;
        try (JarFile jar = new JarFile(jarPath.toFile(), false)) {
            McmodInfo info = McmodInfoParser.read(jar, jarPath.getFileName().toString());
            modId = info.modId();
        }
        if (explicitEntry != null) {
            entryClass = explicitEntry;
        } else {
            entryClass = ModClassLocator.locate(jarPath, profile, modId).modClass();
        }

        List<String> diagnostics = new ArrayList<>();
        List<String> classNames = new ArrayList<>();
        try (ModJarSource source = new ModJarSource(jarPath)) {
            for (String entry : source.classEntryNames()) {
                String name = entry.substring(0, entry.length() - ".class".length()).replace('/', '.');
                if (name.endsWith("module-info")) {
                    continue;
                }
                if (!includeClient && name.contains(".client.")) {
                    continue;
                }
                classNames.add(name);
            }
            LegacyClassLoader loader = new LegacyClassLoader(profile, modId, source, rules,
                    gateway, List.of(), mappings, diagnostics::add);

            System.out.println("mod: " + modId + ", entry: " + entryClass);
            System.out.println("shims: " + shims.size() + ", mappings: methods="
                    + mappings.methodCount() + " fields=" + mappings.fieldCount());
            System.out.println("classes: " + classNames.size()
                    + (includeClient ? " (client included)" : " (client classes skipped)"));

            Map<String, String> failures = new LinkedHashMap<>();
            int loaded = 0;
            for (String name : classNames) {
                try {
                    Class<?> type = Class.forName(name, false, loader);
                    type.getDeclaredMethods();
                    type.getDeclaredFields();
                    loaded++;
                } catch (Throwable t) {
                    Throwable root = t;
                    while (root.getCause() != null && root.getCause() != root) {
                        root = root.getCause();
                    }
                    failures.putIfAbsent(name, root.getClass().getSimpleName() + ": " + root.getMessage());
                }
            }
            System.out.println("loaded: " + loaded + ", failed: " + failures.size());

            Map<String, Integer> missing = new TreeMap<>();
            for (String failure : failures.values()) {
                int marker = failure.indexOf("ClassNotFoundException: ");
                if (marker < 0) {
                    missing.merge(failure, 1, Integer::sum);
                    continue;
                }
                String name = failure.substring(marker + "ClassNotFoundException: ".length());
                int cut = name.indexOf(" is blocked");
                if (cut > 0) {
                    name = name.substring(0, cut);
                }
                missing.merge(name, 1, Integer::sum);
            }
            System.out.println("distinct missing types: " + missing.size());
            System.out.println("\nTop missing types:");
            missing.entrySet().stream()
                    .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                    .limit(80)
                    .forEach(e -> System.out.println("  " + e.getValue() + "x  " + e.getKey()));

            System.out.println("\nFirst 20 failures:");
            failures.entrySet().stream().limit(20)
                    .forEach(e -> System.out.println("  " + e.getKey() + "  <-  " + e.getValue()));

            if (entryClass != null) {
                System.out.println("\nAttempting entry point class + constructor...");
                try {
                    Class<?> modClass = Class.forName(entryClass, true, loader);
                    Object instance = modClass.getDeclaredConstructor().newInstance();
                    System.out.println("OK: constructed " + instance);
                    for (java.lang.reflect.Method method : modClass.getDeclaredMethods()) {
                        System.out.println("  " + method);
                    }
                } catch (Throwable t) {
                    t.printStackTrace(System.out);
                }
            }
        }
    }
}
