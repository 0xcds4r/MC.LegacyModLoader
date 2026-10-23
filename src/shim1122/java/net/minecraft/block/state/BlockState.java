package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class BlockState implements IBlockState {
    private final Block block;
    private final Map<IProperty<?>, Comparable<?>> values;

    public BlockState(Block block) {
        this(block, Map.of());
    }

    private BlockState(Block block, Map<IProperty<?>, Comparable<?>> values) {
        this.block = block;
        this.values = new LinkedHashMap<>(values);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        return (T) values.get(property);
    }

    @Override
    public <T extends Comparable<T>> IBlockState withProperty(IProperty<T> property, T value) {
        Map<IProperty<?>, Comparable<?>> copy = new LinkedHashMap<>(values);
        copy.put(property, value);
        return new BlockState(block, copy);
    }

    @Override
    public IBlockState getBlockState() {
        return this;
    }

    @Override
    public String toString() {
        return "BlockState[" + block + ", " + values + "]";
    }
}
