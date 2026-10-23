package net.minecraft.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator {
    private final IBlockState state;

    public WorldGenTallGrass(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        return false;
    }
}
