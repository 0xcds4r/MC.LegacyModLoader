package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAILookIdle extends EntityAIBase {
    private final EntityLiving entity;

    public EntityAILookIdle(EntityLiving living) {
        this.entity = living;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
