package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIBase {
    private final EntityLiving entity;
    private final boolean closeDoor;

    public EntityAIOpenDoor(EntityLiving living, boolean shouldClose) {
        this.entity = living;
        this.closeDoor = shouldClose;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
