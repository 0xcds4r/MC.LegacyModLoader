package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.function.Predicate;

public class BlockMatcher implements Predicate<IBlockState> {
    private final Block block;

    private BlockMatcher(Block block) {
        this.block = block;
    }

    public static BlockMatcher forBlock(Block block) {
        return new BlockMatcher(block);
    }

    @Override
    public boolean test(IBlockState state) {
        return state != null && state.getBlock() == block;
    }

    public boolean apply(IBlockState state) {
        return test(state);
    }
}
