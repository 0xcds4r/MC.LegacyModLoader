package net.minecraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IBlockAccess {
    IBlockState getBlockState(BlockPos pos);

    TileEntity getTileEntity(BlockPos pos);

    boolean isAirBlock(BlockPos pos);

    int getStrongPower(BlockPos pos, EnumFacing direction);

    boolean isSideSolid(BlockPos pos, EnumFacing side, boolean defaultValue);

    int getCombinedLight(BlockPos pos, int lightValue);
}
