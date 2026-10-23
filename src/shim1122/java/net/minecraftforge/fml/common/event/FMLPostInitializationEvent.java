package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLPostInitializationEvent extends FMLStateEvent {
    public FMLPostInitializationEvent(LegacyModContext context) {
        super(context);
    }
}
