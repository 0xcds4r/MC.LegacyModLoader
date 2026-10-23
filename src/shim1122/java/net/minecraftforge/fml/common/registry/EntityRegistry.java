package net.minecraftforge.fml.common.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public final class EntityRegistry {
    private EntityRegistry() {
    }

    public static void registerModEntity(ResourceLocation registryName, Class<? extends Entity> entityClass,
                                         String entityName, Object mod, int trackingRange,
                                         int updateFrequency, boolean sendsVelocityUpdates) {
    }

    public static void registerModEntity(ResourceLocation registryName, Class<? extends Entity> entityClass,
                                         String entityName, int id, Object mod, int trackingRange,
                                         int updateFrequency, boolean sendsVelocityUpdates) {
    }

    public static void addSpawn(Class<? extends Entity> entityClass, int weightedProb, int min,
                                int max, EnumCreatureType typeOfCreature, Biome... biomes) {
    }

    public static void addSpawn(ResourceLocation entityName, int weightedProb, int min,
                                int max, EnumCreatureType typeOfCreature, Biome... biomes) {
    }

    public static int findNextAvailableId() {
        return 0;
    }

    public static void registerGlobalEntityID(Class<? extends Entity> entityClass, String entityName, int id) {
    }
}
