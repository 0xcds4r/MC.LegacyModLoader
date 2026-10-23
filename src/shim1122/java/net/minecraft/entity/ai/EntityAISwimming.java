package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAISwimming extends EntityAIBase {
    private final EntityLiving entity;

    public EntityAISwimming(EntityLiving entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
