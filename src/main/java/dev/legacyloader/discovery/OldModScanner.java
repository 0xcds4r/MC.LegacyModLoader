package dev.legacyloader.discovery;

import dev.legacyloader.LegacyLoaderConfig;
import dev.legacyloader.api.LegacyProfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

/**
 * Scans {@code old_mods/} in a deterministic order and produces metadata-only
 * descriptors. No mod code is loaded here.
 */
public final class OldModScanner {
    public record ScanResult(List<DiscoveredMod> mods, List<Diagnostic> diagnostics) {
    }

    private final LegacyLoaderConfig config;
    private final LegacyProfile profile;

    public OldModScanner(LegacyLoaderConfig config, LegacyProfile profile) {
        this.config = config;
        this.profile = profile;
    }

    public ScanResult scan() {
        List<Diagnostic> diagnostics = new ArrayList<>();
        Path directory = config.legacyModsDirectory();
        if (!Files.isDirectory(directory)) {
            try {
                Files.createDirectories(directory);
                diagnostics.add(Diagnostic.info("Created " + directory));
            } catch (IOException e) {
                diagnostics.add(Diagnostic.error("Cannot create " + directory + ": " + e.getMessage()));
                return new ScanResult(List.of(), diagnostics);
            }
        }

        List<Path> jars;
        try (Stream<Path> stream = Files.list(directory)) {
            jars = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String name = path.getFileName().toString().toLowerCase(Locale.ROOT);
                        return name.endsWith(".jar") || name.endsWith(".zip");
                    })
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .limit(config.maxModsPerBoot())
                    .toList();
        } catch (IOException e) {
            diagnostics.add(Diagnostic.error("Cannot list " + directory + ": " + e.getMessage()));
            return new ScanResult(List.of(), diagnostics);
        }

        List<DiscoveredMod> mods = new ArrayList<>();
        for (Path jarPath : jars) {
            DiscoveredMod mod = readOne(jarPath, diagnostics);
            if (mod != null) {
                mods.add(mod);
                diagnostics.add(Diagnostic.info("Found " + mod.modId() + " " + mod.info().version()
                        + " (" + jarPath.getFileName() + ", entry=" + mod.modClass() + ")"));
            }
        }
        return new ScanResult(mods, diagnostics);
    }

    private DiscoveredMod readOne(Path jarPath, List<Diagnostic> diagnostics) {
        try (JarFile jar = new JarFile(jarPath.toFile(), false)) {
            Manifest manifest = jar.getManifest();
            if (manifest != null && manifest.getMainAttributes().getValue("FMLCorePlugin") != null) {
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " declares a Forge coremod; coremods are not supported and will be ignored"));
            }
            if (jar.getJarEntry("mixins.legacy.json") != null || hasAnyMixinConfig(jar)) {
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " contains mixin configuration; mixins targeting 1.12.2 cannot be applied"));
            }

            String fallbackId = fallbackId(jarPath);
            McmodInfo info = McmodInfoParser.read(jar, fallbackId);
            String modId = sanitizeModId(info.modId(), fallbackId);
            if (!modId.equals(info.modId())) {
                info = new McmodInfo(modId, info.name(), info.version(), info.mcVersion(),
                        info.description(), info.authors(), info.declaredDependencies(),
                        info.clientSideOnly(), info.serverSideOnly(), info.updateJson(), info.parent());
            }

            if (info.mcVersion() != null && !info.mcVersion().isBlank()
                    && !profile.acceptsMinecraftVersion(info.mcVersion())) {
                ModClassLocator.Result located = ModClassLocator.locate(jarPath, profile, modId);
                ModDependencies dependencies = ModDependencies.parse(
                        info.declaredDependencies(), located.annotationDependencies());
                DiscoveredMod disabled = new DiscoveredMod(jarPath, info, located.modClass(), dependencies);
                disabled.disable("targets Minecraft " + info.mcVersion()
                        + ", profile accepts " + profile.minecraftVersion());
                return disabled;
            }

            if (info.mcVersion() == null || info.mcVersion().isBlank()) {
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " has no mcversion in mcmod.info; assuming " + profile.minecraftVersion()));
            }
            if (info.mcVersion() == null || info.mcVersion().isBlank()) {
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " has no mcversion in mcmod.info; assuming " + profile.minecraftVersion()));
            }

            ModClassLocator.Result located = ModClassLocator.locate(jarPath, profile, modId);
            if (located.modClass() == null) {
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " has no @" + profile.modAnnotationClass() + " class; it will be available as a library only"));
            }
            ModDependencies dependencies = ModDependencies.parse(
                    info.declaredDependencies(), located.annotationDependencies());
            DiscoveredMod mod = new DiscoveredMod(jarPath, info, located.modClass(), dependencies);

            if (info.clientSideOnly() && !config.clientEnvironment() && !config.loadClientModsOnServer()) {
                mod.disable("client-only mod on a dedicated server");
                diagnostics.add(Diagnostic.warn(jarPath.getFileName()
                        + " is client-only and was disabled on a dedicated server"));
            }
            return mod;
        } catch (IOException e) {
            diagnostics.add(Diagnostic.error("Cannot read " + jarPath.getFileName() + ": " + e.getMessage()));
            return null;
        }
    }

    private static boolean hasAnyMixinConfig(JarFile jar) {
        return jar.stream().anyMatch(entry -> {
            String name = entry.getName();
            return !entry.isDirectory() && name.startsWith("mixins.") && name.endsWith(".json");
        });
    }

    private static String fallbackId(Path jarPath) {
        String name = jarPath.getFileName().toString();
        int dot = name.lastIndexOf('.');
        if (dot > 0) {
            name = name.substring(0, dot);
        }
        int dash = name.indexOf('-');
        if (dash > 0) {
            name = name.substring(0, dash);
        }
        String sanitized = name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
        return sanitized.isBlank() ? "unknown" : sanitized;
    }

    private static String sanitizeModId(String modId, String fallback) {
        if (modId == null || modId.isBlank()) {
            return fallback;
        }
        String sanitized = modId.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
        return sanitized.isBlank() ? fallback : sanitized;
    }
}
