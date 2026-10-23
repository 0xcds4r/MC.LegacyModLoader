package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
    public PathNavigateClimber(EntityLiving entity, World world) {
        super(entity, world);
    }
}
