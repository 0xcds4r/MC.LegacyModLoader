package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import dev.legacyloader.bridge.LegacyBlockKind;

public abstract class BlockSlab extends Block {
    public static final net.minecraft.block.properties.PropertyEnum<EnumBlockHalf> HALF =
            net.minecraft.block.properties.PropertyEnum.create("half", EnumBlockHalf.class);

    protected BlockSlab(Material material) {
        super(material);
    }

    @Override
    public LegacyBlockKind legacyKind() {
        return LegacyBlockKind.SLAB;
    }

    public abstract boolean isDouble();

    public enum EnumBlockHalf {
        TOP,
        BOTTOM
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
