package net.minecraftforge.common.property;

import net.minecraft.block.Block;
import net.minecraft.block.properties.Property;
import net.minecraft.block.state.BlockStateContainer;

public class ExtendedBlockState extends BlockStateContainer {
    public ExtendedBlockState(Block block, Property<?>... properties) {
        super(block, properties);
    }

    public ExtendedBlockState(Block block, Property<?>[] properties, IUnlistedProperty<?>[] unlistedProperties) {
        super(block, properties);
    }

    public ExtendedBlockState(Block block, net.minecraft.block.properties.IProperty<?>[] properties,
                              IUnlistedProperty<?>[] unlistedProperties) {
        super(block);
    }
}
