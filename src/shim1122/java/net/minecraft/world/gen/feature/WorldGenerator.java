package net.minecraft.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenerator {
    private final boolean writeToLog;

    public WorldGenerator() {
        this(false);
    }

    public WorldGenerator(boolean writeToLog) {
        this.writeToLog = writeToLog;
    }

    public abstract boolean generate(World world, Random random, BlockPos pos);

    public void setDecorationDefaults() {
    }
}
