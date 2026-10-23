package net.minecraft.world.chunk;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class Chunk {
    private final World world;
    private final int x;
    private final int z;

    public Chunk(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public ChunkPos getPos() {
        return new ChunkPos(x, z);
    }

    public IBlockState getBlockState(BlockPos pos) {
        return world.getBlockState(pos);
    }

    public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType creationMode) {
        return null;
    }

    public int getHeight(BlockPos pos) {
        return 0;
    }

    public int getLightSubtracted(BlockPos pos, int amount) {
        return amount;
    }

    public void markDirty() {
    }

    public enum EnumCreateEntityType {
        IMMEDIATE,
        QUEUED,
        CHECK
    }

    public static final class Pos {
    }
}
