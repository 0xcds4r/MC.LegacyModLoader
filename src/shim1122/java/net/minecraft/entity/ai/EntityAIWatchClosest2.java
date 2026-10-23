package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest {
    public EntityAIWatchClosest2(EntityLiving entity, Class<? extends Entity> watchedClass,
                                 float distance, float chance) {
        super(entity, watchedClass, distance);
    }
}
