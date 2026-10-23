package net.minecraft.block;

public interface IGrowable {
    boolean canGrow(net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos,
                    net.minecraft.block.state.IBlockState state, boolean isClient);

    boolean canUseBonemeal(net.minecraft.world.World world, java.util.Random random,
                           net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state);

    void grow(net.minecraft.world.World world, java.util.Random random,
              net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state);
}
