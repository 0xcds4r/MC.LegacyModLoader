package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockCrops extends BlockBush {
    public static final net.minecraft.block.properties.PropertyInteger AGE =
            net.minecraft.block.properties.PropertyInteger.create("age", 0, 7);

    public BlockCrops() {
        super(Material.PLANTS);
    }

    @Override
    public dev.legacyloader.bridge.LegacyBlockKind legacyKind() {
        return dev.legacyloader.bridge.LegacyBlockKind.CROPS;
    }

    public int getMaxAge() {
        return 7;
    }

    public int getAge(net.minecraft.block.state.IBlockState state) {
        return 0;
    }

    public net.minecraft.block.state.IBlockState withAge(int age) {
        return getDefaultState();
    }
}
