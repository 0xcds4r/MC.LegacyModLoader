package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityArrow extends EntityThrowable {
    public Entity shootingEntity;
    public double damage = 2.0D;

    public EntityArrow(World world) {
        super(world);
    }

    public EntityArrow(World world, EntityLivingBase shooter) {
        super(world, shooter);
    }

    public void setAim(Entity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
    }
}
