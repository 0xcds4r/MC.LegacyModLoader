package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockLog extends Block {
    public BlockLog() {
        super(Material.WOOD);
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    public enum EnumAxis {
        X,
        Y,
        Z,
        NONE
    }
}
