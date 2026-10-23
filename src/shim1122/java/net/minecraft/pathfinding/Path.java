package net.minecraft.pathfinding;

import net.minecraft.util.math.BlockPos;

public class Path {
    public boolean isFinished() {
        return true;
    }

    public BlockPos getCurrentPos() {
        return BlockPos.ORIGIN;
    }

    public int getCurrentPathLength() {
        return 0;
    }
}
