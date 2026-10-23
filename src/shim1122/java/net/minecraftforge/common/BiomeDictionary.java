package net.minecraftforge.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class BiomeDictionary {
    public static class Type {
        private static final Set<String> NAMES = new LinkedHashSet<>();
        public static final Type HOT = new Type("HOT");
        public static final Type COLD = new Type("COLD");
        public static final Type SPARSE = new Type("SPARSE");
        public static final Type DENSE = new Type("DENSE");
        public static final Type WET = new Type("WET");
        public static final Type DRY = new Type("DRY");
        public static final Type SAVANNA = new Type("SAVANNA");
        public static final Type CONIFEROUS = new Type("CONIFEROUS");
        public static final Type JUNGLE = new Type("JUNGLE");
        public static final Type SPOOKY = new Type("SPOOKY");
        public static final Type DEAD = new Type("DEAD");
        public static final Type LUSH = new Type("LUSH");
        public static final Type NETHER = new Type("NETHER");
        public static final Type END = new Type("END");
        public static final Type MUSHROOM = new Type("MUSHROOM");
        public static final Type MAGICAL = new Type("MAGICAL");
        public static final Type SANDY = new Type("SANDY");
        public static final Type SNOWY = new Type("SNOWY");
        public static final Type WASTELAND = new Type("WASTELAND");
        public static final Type BEACH = new Type("BEACH");
        public static final Type FOREST = new Type("FOREST");
        public static final Type OCEAN = new Type("OCEAN");
        public static final Type RIVER = new Type("RIVER");
        public static final Type SWAMP = new Type("SWAMP");
        public static final Type HILLS = new Type("HILLS");
        public static final Type MOUNTAIN = new Type("MOUNTAIN");
        public static final Type PLAINS = new Type("PLAINS");
        public static final Type WATER = new Type("WATER");

        private final String name;

        private Type(String name) {
            this.name = name;
            NAMES.add(name);
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static Set<Type> getTypes(net.minecraft.world.biome.Biome biome) {
        return Set.of();
    }

    public static Set<net.minecraft.world.biome.Biome> getBiomes(Type type) {
        return Set.of();
    }
}
