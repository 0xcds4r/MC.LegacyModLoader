package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLLoadCompleteEvent extends FMLStateEvent {
    public FMLLoadCompleteEvent(LegacyModContext context) {
        super(context);
    }
}
