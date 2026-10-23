package net.minecraftforge.fml.common;

public class ModContainer {
    private final String modId;
    private final String name;
    private final String version;

    public ModContainer(String modId, String name, String version) {
        this.modId = modId;
        this.name = name;
        this.version = version;
    }

    public String getModId() {
        return modId;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public ModMetadata getMetadata() {
        ModMetadata metadata = new ModMetadata();
        metadata.modId = modId;
        metadata.name = name;
        metadata.version = version;
        return metadata;
    }
}
