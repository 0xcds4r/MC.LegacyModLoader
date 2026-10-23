package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;
import net.minecraft.command.ICommand;

public class FMLServerStartingEvent extends FMLEvent {
    public FMLServerStartingEvent(LegacyModContext context) {
        super(context);
    }

    public void registerServerCommand(ICommand command) {
        // Dedicated server commands are a later phase; accepting the call lets
        // mods finish their lifecycle without crashing.
    }
}
