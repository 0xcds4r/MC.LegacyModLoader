package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockStoneSlab extends BlockSlab {
    private final boolean doubleSlab;

    public BlockStoneSlab(boolean isDouble) {
        super(Material.ROCK);
        this.doubleSlab = isDouble;
    }

    @Override
    public boolean isDouble() {
        return doubleSlab;
    }
}
