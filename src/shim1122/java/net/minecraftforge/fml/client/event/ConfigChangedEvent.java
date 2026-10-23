package net.minecraftforge.fml.client.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ConfigChangedEvent extends Event {
    private final String modID;
    private final String configID;
    private final boolean isWorldRunning;
    private final boolean requiresMcRestart;

    public ConfigChangedEvent(String modID, String configID, boolean isWorldRunning, boolean requiresMcRestart) {
        this.modID = modID;
        this.configID = configID;
        this.isWorldRunning = isWorldRunning;
        this.requiresMcRestart = requiresMcRestart;
    }

    public String getModID() {
        return modID;
    }

    public String getConfigID() {
        return configID;
    }

    public boolean isWorldRunning() {
        return isWorldRunning;
    }

    public boolean requiresMcRestart() {
        return requiresMcRestart;
    }

    public static class OnConfigChangedEvent extends ConfigChangedEvent {
        public OnConfigChangedEvent(String modID, String configID, boolean isWorldRunning, boolean requiresMcRestart) {
            super(modID, configID, isWorldRunning, requiresMcRestart);
        }
    }
}
