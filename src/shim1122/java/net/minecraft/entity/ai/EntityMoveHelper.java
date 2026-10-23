package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityMoveHelper {
    protected final EntityLiving entity;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double speed;

    public EntityMoveHelper(EntityLiving entity) {
        this.entity = entity;
    }

    public void setMoveTo(double x, double y, double z, double speed) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }
}
