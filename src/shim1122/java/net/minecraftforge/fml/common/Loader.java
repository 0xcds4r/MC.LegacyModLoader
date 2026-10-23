package net.minecraftforge.fml.common;

import dev.legacyloader.bridge.BridgeHolder;

import java.util.Collections;
import java.util.Map;

public class Loader {
    private static final Loader INSTANCE = new Loader();

    public static Loader instance() {
        return INSTANCE;
    }

    public static boolean isModLoaded(String modId) {
        try {
            return BridgeHolder.require().isModLoaded(modId);
        } catch (IllegalStateException ignored) {
            return false;
        }
    }

    public ModContainer activeModContainer() {
        return null;
    }

    public Map<String, ModContainer> getModList() {
        return Collections.emptyMap();
    }

    public Map<String, ModContainer> getIndexedModList() {
        return getModList();
    }

    public boolean isModActive(ModContainer container) {
        return container != null && isModLoaded(container.getModId());
    }
}
