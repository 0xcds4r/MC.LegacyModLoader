package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityLlamaSpit extends EntityFireball {
    public EntityLlamaSpit(World world) {
        super(world);
    }

    public EntityLlamaSpit(World world, EntityLivingBase shooter) {
        super(world);
        setPosition(shooter.posX, shooter.posY, shooter.posZ);
    }
}
