package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntitySmallFireball extends Entity {
    public EntitySmallFireball(World world) {
        super(world);
    }

    public EntitySmallFireball(World world, double x, double y, double z) {
        super(world);
        setPosition(x, y, z);
    }

    public EntitySmallFireball(World world, net.minecraft.entity.EntityLivingBase shooter,
                               double accelX, double accelY, double accelZ) {
        super(world);
        setPosition(shooter.posX, shooter.posY, shooter.posZ);
    }
}
