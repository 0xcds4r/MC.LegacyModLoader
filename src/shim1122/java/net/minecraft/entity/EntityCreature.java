package net.minecraft.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityCreature extends EntityLiving {
    public final List<EntityAIBase> tasks = new ArrayList<>();
    public final List<EntityAIBase> targetTasks = new ArrayList<>();
    private EntityLivingBase attackTarget;

    public EntityCreature(World world) {
        super(world);
    }

    public EntityLivingBase getAttackTarget() {
        return attackTarget;
    }

    public void setAttackTarget(EntityLivingBase target) {
        this.attackTarget = target;
    }

    public boolean isWithinHomeDistanceCurrentPosition() {
        return true;
    }
}
