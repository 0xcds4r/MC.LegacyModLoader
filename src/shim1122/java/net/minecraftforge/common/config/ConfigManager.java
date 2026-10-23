package net.minecraftforge.common.config;

public final class ConfigManager {
    private ConfigManager() {
    }

    public static void sync(String modId, Config.Type type) {
        // no-op: automatic configs are not persisted yet
    }
}
