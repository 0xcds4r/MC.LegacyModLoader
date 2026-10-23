package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

public class EntityAIAttackMelee extends EntityAIBase {
    protected final EntityCreature attacker;
    protected final double speedTowardsTarget;
    protected final boolean longMemory;

    public EntityAIAttackMelee(EntityCreature creature, double speed, boolean useLongMemory) {
        this.attacker = creature;
        this.speedTowardsTarget = speed;
        this.longMemory = useLongMemory;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }

    @Override
    public void startExecuting() {
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double distance) {
    }
}
