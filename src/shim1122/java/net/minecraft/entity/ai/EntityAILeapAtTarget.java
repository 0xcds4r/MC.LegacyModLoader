package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAILeapAtTarget extends EntityAIBase {
    private final EntityLiving leaper;
    private final float leapMotionY;

    public EntityAILeapAtTarget(EntityLiving leapingEntity, float leapMotionY) {
        this.leaper = leapingEntity;
        this.leapMotionY = leapMotionY;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
