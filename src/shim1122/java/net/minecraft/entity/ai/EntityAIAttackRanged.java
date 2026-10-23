package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;

public class EntityAIAttackRanged extends EntityAIBase {
    private final IRangedAttackMob attacker;
    private final double moveSpeed;
    private final int attackInterval;
    private final float maxAttackDistance;

    public EntityAIAttackRanged(IRangedAttackMob attacker, double moveSpeed,
                                int attackInterval, float maxAttackDistance) {
        this.attacker = attacker;
        this.moveSpeed = moveSpeed;
        this.attackInterval = attackInterval;
        this.maxAttackDistance = maxAttackDistance;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
