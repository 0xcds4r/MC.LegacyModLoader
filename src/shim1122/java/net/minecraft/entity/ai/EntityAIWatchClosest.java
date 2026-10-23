package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest extends EntityAIBase {
    protected final EntityLiving entity;
    protected final Class<? extends Entity> watchedClass;
    protected final float maxDistance;

    public EntityAIWatchClosest(EntityLiving entity, Class<? extends Entity> watchedClass, float distance) {
        this.entity = entity;
        this.watchedClass = watchedClass;
        this.maxDistance = distance;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
