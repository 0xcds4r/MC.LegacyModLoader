package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

public class FMLConstructionEvent extends FMLEvent {
    public FMLConstructionEvent(LegacyModContext context) {
        super(context);
    }
}
