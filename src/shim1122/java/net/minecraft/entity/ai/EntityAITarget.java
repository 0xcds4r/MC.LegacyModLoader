package net.minecraft.entity.ai;

public abstract class EntityAITarget extends EntityAIBase {
    protected final net.minecraft.entity.EntityCreature taskOwner;
    private final boolean shouldCheckSight;
    private final boolean nearbyOnly;

    public EntityAITarget(net.minecraft.entity.EntityCreature creature, boolean checkSight) {
        this(creature, checkSight, false);
    }

    public EntityAITarget(net.minecraft.entity.EntityCreature creature, boolean checkSight, boolean nearbyOnly) {
        this.taskOwner = creature;
        this.shouldCheckSight = checkSight;
        this.nearbyOnly = nearbyOnly;
    }
}
