package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLStateEvent extends FMLEvent {
    public FMLStateEvent(LegacyModContext context) {
        super(context);
    }
}
