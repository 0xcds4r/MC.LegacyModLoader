package net.minecraft.potion;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class PotionType extends IForgeRegistryEntry.Impl<PotionType> {
    public static final net.minecraft.util.registry.RegistryNamespacedDefaultedByKey<net.minecraft.util.ResourceLocation, PotionType> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespacedDefaultedByKey<>(
                    new net.minecraft.util.ResourceLocation("empty"));
    private final String name;
    private final List<PotionEffect> effects = new ArrayList<>();

    public PotionType(String name, PotionEffect... effects) {
        this.name = name;
        for (PotionEffect effect : effects) {
            this.effects.add(effect);
        }
    }

    public String getNamePrefixed(String prefix) {
        return prefix + name;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public boolean hasInstantEffect() {
        return false;
    }

    @Override
    public Class<PotionType> getRegistrySuperType() {
        return PotionType.class;
    }
}
