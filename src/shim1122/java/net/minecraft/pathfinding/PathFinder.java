package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class PathFinder {
    private final PathNavigate navigate;

    public PathFinder(PathNavigate navigate) {
        this.navigate = navigate;
    }

    public PathEntity findPath(IBlockAccess world, EntityLiving entity, BlockPos target, float maxDistance) {
        return null;
    }
}
