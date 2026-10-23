package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLInitializationEvent extends FMLStateEvent {
    public FMLInitializationEvent(LegacyModContext context) {
        super(context);
    }
}
