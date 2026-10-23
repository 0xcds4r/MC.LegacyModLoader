package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockStairs extends Block {
    private final IBlockState modelState;

    public BlockStairs(Block block, int meta) {
        this(block.getDefaultState());
    }

    public BlockStairs(IBlockState modelState) {
        super(Material.ROCK);
        this.modelState = modelState;
    }

    @Override
    public dev.legacyloader.bridge.LegacyBlockKind legacyKind() {
        return dev.legacyloader.bridge.LegacyBlockKind.STAIRS;
    }

    public IBlockState getModelState() {
        return modelState;
    }
}
