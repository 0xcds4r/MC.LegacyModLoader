package net.minecraft.dispenser;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockSource extends IPosition {
    World getWorld();

    BlockPos getBlockPos();

    net.minecraft.block.state.IBlockState getBlockState();
}
