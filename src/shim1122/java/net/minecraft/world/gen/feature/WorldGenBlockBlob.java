package net.minecraft.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenBlockBlob extends WorldGenerator {
    private final IBlockState state;
    private final int startRadius;

    public WorldGenBlockBlob(IBlockState state, int startRadius) {
        this.state = state;
        this.startRadius = startRadius;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        return false;
    }
}
