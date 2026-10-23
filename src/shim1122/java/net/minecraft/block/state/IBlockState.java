package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public interface IBlockState {
    Block getBlock();

    <T extends Comparable<T>> T getValue(IProperty<T> property);

    <T extends Comparable<T>> IBlockState withProperty(IProperty<T> property, T value);

    IBlockState getBlockState();
}
