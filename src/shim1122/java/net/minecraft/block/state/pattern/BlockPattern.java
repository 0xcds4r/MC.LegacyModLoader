package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockPattern {
    private final String[] pattern;
    private final int width;
    private final int height;
    private final int depth;

    public BlockPattern(String... pattern) {
        this.pattern = pattern.clone();
        this.height = pattern.length;
        this.width = height == 0 ? 0 : pattern[0].length();
        this.depth = 1;
    }

    public BlockPattern(Factory factory) {
        this(factory.pattern());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public PatternHelper translateToWorld(World world, BlockPos pos) {
        return new PatternHelper(world, pos);
    }

    public static class PatternHelper {
        private final World world;
        private final BlockPos pos;

        public PatternHelper(World world, BlockPos pos) {
            this.world = world;
            this.pos = pos;
        }

        public boolean match() {
            return false;
        }

        public BlockPos getPos() {
            return pos;
        }
    }

    public interface Factory {
        String[] pattern();

        Map<Character, Predicate<IBlockState>> blockMatchers();
    }

    public interface Predicate<T> extends java.util.function.Predicate<T> {
    }
}
