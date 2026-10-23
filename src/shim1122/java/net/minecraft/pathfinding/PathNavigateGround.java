package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class PathNavigateGround extends PathNavigate {
    public PathNavigateGround(EntityLiving entity, World world) {
        super(entity);
    }

    public void setBreakDoors(boolean canBreakDoors) {
    }

    public void setEnterDoors(boolean enterDoors) {
    }

    public boolean getCanBreakDoors() {
        return false;
    }
}
