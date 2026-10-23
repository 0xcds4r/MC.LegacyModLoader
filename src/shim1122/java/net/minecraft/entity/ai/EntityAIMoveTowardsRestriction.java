package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIMoveTowardsRestriction extends EntityAIBase {
    private final EntityCreature creature;
    private final double movementSpeed;

    public EntityAIMoveTowardsRestriction(EntityCreature creature, double speed) {
        this.creature = creature;
        this.movementSpeed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
