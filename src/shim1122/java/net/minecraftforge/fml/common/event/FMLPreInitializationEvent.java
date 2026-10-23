package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLPreInitializationEvent extends FMLStateEvent {
    public FMLPreInitializationEvent(LegacyModContext context) {
        super(context);
    }
}
