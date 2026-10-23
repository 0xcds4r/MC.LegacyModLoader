package net.minecraftforge.registries;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

/**
 * Entry point called by the loader. Shim-side so it can use the real shim event
 * types; the runtime only needs the method name.
 */
public final class LegacyRegistryEvents {
    private LegacyRegistryEvents() {
    }

    public static void fire(String registryName) {
        BridgeRegistry registry = new BridgeRegistry(registryName);
        String modId = dev.legacyloader.bridge.BridgeHolder.require().currentContext().modId();
        MinecraftForge.EVENT_BUS.postForMod(new RegistryEvent.Register(registry), modId);
    }
}
