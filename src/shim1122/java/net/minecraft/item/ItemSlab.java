package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

public class ItemSlab extends ItemBlock {
    private final BlockSlab half;
    private final BlockSlab doubleSlab;

    public ItemSlab(Block block, BlockSlab half, BlockSlab doubleSlab) {
        super(block);
        this.half = half;
        this.doubleSlab = doubleSlab;
    }

    public BlockSlab getHalf() {
        return half;
    }

    public BlockSlab getDoubleSlab() {
        return doubleSlab;
    }
}
