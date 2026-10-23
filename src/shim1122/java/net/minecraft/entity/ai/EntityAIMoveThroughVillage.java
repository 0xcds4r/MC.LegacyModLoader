package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIMoveThroughVillage extends EntityAIBase {
    private final EntityCreature entity;
    private final double movementSpeed;
    private final boolean isNocturnal;

    public EntityAIMoveThroughVillage(EntityCreature creature, double speed, boolean nocturnal) {
        this.entity = creature;
        this.movementSpeed = speed;
        this.isNocturnal = nocturnal;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
