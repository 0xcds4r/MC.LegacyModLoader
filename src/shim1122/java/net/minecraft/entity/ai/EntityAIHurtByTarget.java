package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIHurtByTarget extends EntityAITarget {
    private final boolean entityCallsForHelp;

    public EntityAIHurtByTarget(EntityCreature creature, boolean callsForHelp) {
        super(creature, false);
        this.entityCallsForHelp = callsForHelp;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
