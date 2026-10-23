package net.minecraft.util;

import dev.legacyloader.bridge.LegacySoundView;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoundEvent implements IForgeRegistryEntry<SoundEvent>, LegacySoundView {
    private final ResourceLocation registryName;
    private final float volume = 1.0F;
    private final float pitch = 1.0F;

    public SoundEvent(ResourceLocation soundName) {
        this.registryName = soundName;
    }

    public SoundEvent(ResourceLocation soundName, float range) {
        this.registryName = soundName;
    }

    public ResourceLocation getSoundName() {
        return registryName;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public SoundEvent setRegistryName(ResourceLocation name) {
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public Class<SoundEvent> getRegistrySuperType() {
        return SoundEvent.class;
    }

    @Override
    public String legacySoundNamespace() {
        return registryName == null ? "minecraft" : registryName.getNamespace();
    }

    @Override
    public String legacySoundPath() {
        return registryName == null ? "empty" : registryName.getPath();
    }
}
