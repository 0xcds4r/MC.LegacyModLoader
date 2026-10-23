package net.minecraft.potion;

import net.minecraftforge.registries.IForgeRegistryEntry;

public class Potion extends IForgeRegistryEntry.Impl<Potion> {
    public static final net.minecraft.util.registry.RegistryNamespaced<net.minecraft.util.ResourceLocation, Potion> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespaced<>();
    private final boolean badEffect;
    private final int liquidColor;
    private String name = "";
    private boolean beneficial;
    private int iconIndex;

    public Potion(boolean badEffect, int liquidColor) {
        this.badEffect = badEffect;
        this.liquidColor = liquidColor;
    }

    public Potion setPotionName(String name) {
        this.name = name;
        return this;
    }

    public Potion setIconIndex(int x, int y) {
        this.iconIndex = y * 8 + x;
        return this;
    }

    public Potion setBeneficial() {
        this.beneficial = true;
        return this;
    }

    public boolean isBadEffect() {
        return badEffect;
    }

    public boolean isBeneficial() {
        return beneficial;
    }

    public int getLiquidColor() {
        return liquidColor;
    }

    public String getName() {
        return name;
    }

    public int getStatusIconIndex() {
        return iconIndex;
    }

    @Override
    public Class<Potion> getRegistrySuperType() {
        return Potion.class;
    }
}
