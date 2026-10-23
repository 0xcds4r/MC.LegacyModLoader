package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class BlockStateContainer {
    private final Block block;
    private final List<Property<?>> properties;
    private final IBlockState baseState;

    public BlockStateContainer(Block block, Property<?>... properties) {
        this.block = block;
        this.properties = List.of(properties);
        this.baseState = new BlockState(block);
    }

    public BlockStateContainer(Block block, net.minecraft.block.properties.IProperty<?>[] properties) {
        this(block);
    }

    public Block getBlock() {
        return block;
    }

    public List<Property<?>> getProperties() {
        return properties;
    }

    public IBlockState getBaseState() {
        return baseState;
    }

    public static class Builder {
        private final Block block;
        private final List<Property<?>> properties = new ArrayList<>();

        public Builder(Block block) {
            this.block = block;
        }

        public Builder add(Property<?>... properties) {
            this.properties.addAll(List.of(properties));
            return this;
        }

        public BlockStateContainer build() {
            return new BlockStateContainer(block, properties.toArray(Property[]::new));
        }
    }
}
