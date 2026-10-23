package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIWander extends EntityAIBase {
    private final EntityCreature entity;
    private final double speed;

    public EntityAIWander(EntityCreature creature, double speed) {
        this.entity = creature;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
