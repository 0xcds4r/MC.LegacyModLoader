package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockPlanks extends Block {
    public BlockPlanks() {
        super(Material.WOOD);
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    public enum EnumType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
