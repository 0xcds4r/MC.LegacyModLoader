package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityFireball extends Entity {
    public EntityFireball(World world) {
        super(world);
    }

    public EntityFireball(World world, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(world);
        setPosition(x, y, z);
    }
}
