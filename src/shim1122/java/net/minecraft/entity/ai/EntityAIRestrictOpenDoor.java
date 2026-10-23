package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
    public EntityAIRestrictOpenDoor(EntityCreature creature) {
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
