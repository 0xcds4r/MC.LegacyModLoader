package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;

public class PathNavigate {
    protected final EntityLiving entity;

    public PathNavigate(EntityLiving entity) {
        this.entity = entity;
    }

    public boolean tryMoveToXYZ(double x, double y, double z, double speed) {
        return false;
    }

    public boolean tryMoveToEntityLiving(net.minecraft.entity.Entity entity, double speed) {
        return false;
    }

    public void clearPath() {
    }

    public boolean noPath() {
        return true;
    }

    public BlockPos getTargetPos() {
        return entity.getPosition();
    }

    public void setSpeed(double speed) {
    }
}
