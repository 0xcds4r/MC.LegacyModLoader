package net.minecraftforge.common;

import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BiomeManager {
    private static final Map<BiomeType, List<BiomeEntry>> BIOMES = new EnumMap<>(BiomeType.class);

    public static void addBiome(BiomeType type, Biome biome, int weight) {
        BIOMES.computeIfAbsent(type, ignored -> new ArrayList<>()).add(new BiomeEntry(biome, weight));
    }

    public static com.google.common.collect.ImmutableList<BiomeEntry> getBiomes(BiomeType type) {
        return com.google.common.collect.ImmutableList.copyOf(BIOMES.getOrDefault(type, List.of()));
    }

    public enum BiomeType {
        DESERT,
        WARM,
        COOL,
        ICY
    }

    public static class BiomeEntry {
        public final Biome biome;
        public final int itemWeight;

        public BiomeEntry(Biome biome, int itemWeight) {
            this.biome = biome;
            this.itemWeight = itemWeight;
        }
    }
}
