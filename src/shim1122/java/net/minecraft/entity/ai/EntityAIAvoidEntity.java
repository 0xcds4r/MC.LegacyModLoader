package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public class EntityAIAvoidEntity extends EntityAIBase {
    public EntityAIAvoidEntity(EntityCreature entity, Class<? extends Entity> avoidTarget,
                               float distanceFromTarget, double farSpeed, double nearSpeed) {
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
