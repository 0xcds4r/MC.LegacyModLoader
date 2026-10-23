package net.minecraft.util.math;

import net.minecraft.entity.Entity;

public class ChunkPos {
    public final int x;
    public final int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkPos(BlockPos pos) {
        this(pos.getX() >> 4, pos.getZ() >> 4);
    }

    public ChunkPos(Entity entity) {
        this(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public long asLong() {
        return ((long) x & 0xFFFFFFFFL) | (((long) z & 0xFFFFFFFFL) << 32);
    }

    public static long asLong(int x, int z) {
        return ((long) x & 0xFFFFFFFFL) | (((long) z & 0xFFFFFFFFL) << 32);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ChunkPos pos && pos.x == x && pos.z == z;
    }

    @Override
    public int hashCode() {
        return 31 * x + z;
    }

    @Override
    public String toString() {
        return "ChunkPos(" + x + ", " + z + ")";
    }
}
