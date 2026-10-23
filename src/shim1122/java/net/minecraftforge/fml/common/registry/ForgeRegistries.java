package net.minecraftforge.fml.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.BridgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Forge 1.12.2 static registry handles. They are backed by the bridge, so
 * registrations made through ForgeRegistries follow the same path as
 * RegistryEvent registrations.
 */
@SuppressWarnings("unchecked")
public final class ForgeRegistries {
    public static final IForgeRegistry<Block> BLOCKS =
            (IForgeRegistry<Block>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:blocks");
    public static final IForgeRegistry<Item> ITEMS =
            (IForgeRegistry<Item>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:items");
    public static final IForgeRegistry<SoundEvent> SOUNDS =
            (IForgeRegistry<SoundEvent>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:sounds");
    public static final IForgeRegistry<Potion> POTIONS =
            (IForgeRegistry<Potion>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:potions");
    public static final IForgeRegistry<Biome> BIOMES =
            (IForgeRegistry<Biome>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:biomes");
    public static final IForgeRegistry<EntityEntry> ENTITIES =
            (IForgeRegistry<EntityEntry>) (IForgeRegistry<?>) new BridgeRegistry("minecraft:entities");
    @SuppressWarnings("rawtypes")
    public static final IForgeRegistry RECIPES = new BridgeRegistry("minecraft:recipes");

    private ForgeRegistries() {
    }
}
