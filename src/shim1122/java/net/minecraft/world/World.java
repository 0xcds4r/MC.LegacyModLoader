package net.minecraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Signature-level world shim. Mutations are not bridged yet: a simple
 * registration-only mod never calls them, and silently doing nothing would be
 * worse than a precise failure. The error message names the missing bridge.
 */
public class World implements IBlockAccess {
    public boolean isRemote;
    private long worldTime;

    public World() {
    }

    public boolean isRemote() {
        return isRemote;
    }

    public IBlockState getBlockState(BlockPos pos) {
        throw unsupported("World.getBlockState");
    }

    public boolean setBlockState(BlockPos pos, IBlockState state) {
        throw unsupported("World.setBlockState");
    }

    public boolean setBlockState(BlockPos pos, IBlockState state, int flags) {
        throw unsupported("World.setBlockState(flags)");
    }

    public boolean isAirBlock(BlockPos pos) {
        throw unsupported("World.isAirBlock");
    }

    public TileEntity getTileEntity(BlockPos pos) {
        throw unsupported("World.getTileEntity");
    }

    public void setTileEntity(BlockPos pos, TileEntity tileEntity) {
        throw unsupported("World.setTileEntity");
    }

    public void spawnEntity(Entity entity) {
        throw unsupported("World.spawnEntity");
    }

    public long getWorldTime() {
        return worldTime;
    }

    public void setWorldTime(long time) {
        this.worldTime = time;
    }

    public boolean isDaytime() {
        return worldTime % 24000L < 12000L;
    }

    @Override
    public int getStrongPower(BlockPos pos, net.minecraft.util.EnumFacing direction) {
        return 0;
    }

    @Override
    public boolean isSideSolid(BlockPos pos, net.minecraft.util.EnumFacing side, boolean defaultValue) {
        return defaultValue;
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return lightValue;
    }

    private static UnsupportedOperationException unsupported(String operation) {
        return new UnsupportedOperationException(
                operation + " is not bridged yet. Add a World bridge method before using this mod.");
    }
}
