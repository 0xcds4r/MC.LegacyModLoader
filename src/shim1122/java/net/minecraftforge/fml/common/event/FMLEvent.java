package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FMLEvent {
    private final LegacyModContext context;

    public FMLEvent(LegacyModContext context) {
        this.context = context;
    }

    public LegacyModContext getLegacyContext() {
        return context;
    }

    public Side getSide() {
        return context.side() == dev.legacyloader.bridge.LegacySide.CLIENT ? Side.CLIENT : Side.SERVER;
    }

    public Logger getModLog() {
        return LogManager.getLogger(context.loggerName());
    }

    public File getSuggestedConfigurationFile() {
        return context.configDirectory().resolve(context.modId() + ".cfg").toFile();
    }

    public File getModConfigurationDirectory() {
        return context.configDirectory().toFile();
    }

    public ModMetadata getModMetadata() {
        ModMetadata metadata = new ModMetadata();
        metadata.modId = context.modId();
        metadata.name = context.modName();
        metadata.version = context.modVersion();
        return metadata;
    }
}
