package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityThrowable extends Entity {
    protected EntityLivingBase thrower;
    protected int ticksInAir;

    public EntityThrowable(World world) {
        super(world);
    }

    public EntityThrowable(World world, EntityLivingBase thrower) {
        super(world);
        this.thrower = thrower;
        setPosition(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
    }

    public EntityThrowable(World world, double x, double y, double z) {
        super(world);
        setPosition(x, y, z);
    }

    public EntityLivingBase getThrower() {
        return thrower;
    }
}
