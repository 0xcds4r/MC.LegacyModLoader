package dev.legacyloader;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Set;

/**
 * Immutable loader configuration. Kept free of Minecraft types so the discovery
 * and classloader packages can be unit-tested without booting the game.
 */
public record LegacyLoaderConfig(
        Path gameDirectory,
        Path legacyModsDirectory,
        boolean strictIsolation,
        boolean clientEnvironment,
        boolean loadClientModsOnServer,
        Set<String> acceptedMinecraftVersions,
        int maxModsPerBoot
) {
    private static final Logger LOG = LoggerFactory.getLogger("legacyloader");

    public static LegacyLoaderConfig defaults() {
        Path gameDir = FabricLoader.getInstance().getGameDir();
        Path legacyDir = gameDir.resolve("old_mods");
        boolean client = FabricLoader.getInstance().getEnvironmentType()
                == net.fabricmc.api.EnvType.CLIENT;

        return new LegacyLoaderConfig(
                gameDir,
                legacyDir,
                true,
                client,
                client,
                Set.of("1.12.2"),
                64
        );
    }

    public LegacyLoaderConfig {
        gameDirectory = gameDirectory.toAbsolutePath().normalize();
        legacyModsDirectory = legacyModsDirectory.toAbsolutePath().normalize();
        acceptedMinecraftVersions = Set.copyOf(acceptedMinecraftVersions);
    }

    public void logSummary() {
        LOG.info("Legacy Mod Loader: gameDir={}, oldMods={}, strictIsolation={}, side={}, acceptedMc={}",
                gameDirectory, legacyModsDirectory, strictIsolation,
                clientEnvironment ? "client" : "server", acceptedMinecraftVersions);
    }
}
