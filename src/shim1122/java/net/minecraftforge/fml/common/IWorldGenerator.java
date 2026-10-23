package net.minecraftforge.fml.common;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public interface IWorldGenerator {
    void generate(Random random, int chunkX, int chunkZ, World world,
                  IChunkGenerator chunkGenerator, IChunkProvider chunkProvider);
}
