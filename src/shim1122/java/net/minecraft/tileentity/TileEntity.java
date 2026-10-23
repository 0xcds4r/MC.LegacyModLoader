package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 1.12.2 tile entity shim. Registration of tile entities and ticking are later
 * phases; the type exists so common method descriptors resolve.
 */
public class TileEntity {
    private World world;
    private BlockPos pos = BlockPos.ORIGIN;

    public TileEntity() {
    }

    public void readFromNBT(NBTTagCompound compound) {
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return compound;
    }

    public void update() {
    }

    public void markDirty() {
    }

    public boolean hasWorld() {
        return world != null;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public Block getBlockType() {
        return null;
    }

    public int getBlockMetadata() {
        return 0;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
