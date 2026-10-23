package net.minecraftforge.fml.common.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;

public class EntityEntry implements IForgeRegistryEntry<EntityEntry> {
    private final Class<? extends Entity> entityClass;
    private final String entityName;
    private final int trackingRange;
    private final int updateFrequency;
    private final boolean sendsVelocityUpdates;
    private ResourceLocation registryName;

    public EntityEntry(Class<? extends Entity> entityClass, String entityName) {
        this(entityClass, entityName, 64, 1, true);
    }

    public EntityEntry(Class<? extends Entity> entityClass, String entityName, int trackingRange) {
        this(entityClass, entityName, trackingRange, 1, true);
    }

    public EntityEntry(Class<? extends Entity> entityClass, String entityName,
                       int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        this.entityClass = entityClass;
        this.entityName = entityName;
        this.trackingRange = trackingRange;
        this.updateFrequency = updateFrequency;
        this.sendsVelocityUpdates = sendsVelocityUpdates;
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getTrackingRange() {
        return trackingRange;
    }

    public int getUpdateFrequency() {
        return updateFrequency;
    }

    public boolean sendsVelocityUpdates() {
        return sendsVelocityUpdates;
    }

    public Entity newInstance(World world) {
        try {
            return entityClass.getConstructor(World.class).newInstance(world);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot instantiate " + entityClass.getName(), e);
        }
    }

    @Override
    public EntityEntry setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName != null ? registryName
                : new ResourceLocation(entityName.toLowerCase(Locale.ROOT));
    }

    @Override
    public Class<EntityEntry> getRegistrySuperType() {
        return EntityEntry.class;
    }
}
