package net.minecraftforge.common.property;

import net.minecraft.block.properties.Property;

public final class Properties {
    private Properties() {
    }

    public static <T extends Comparable<T>> IUnlistedProperty<T> get(Property<T> property) {
        return new PropertyAdapter<>(property);
    }

    public static class PropertyAdapter<V extends Comparable<V>> implements IUnlistedProperty<V> {
        private final net.minecraft.block.properties.IProperty<V> property;

        public PropertyAdapter(net.minecraft.block.properties.IProperty<V> property) {
            this.property = property;
        }

        public net.minecraft.block.properties.IProperty<V> getProperty() {
            return property;
        }

        @Override
        public String getName() {
            return property.getName();
        }

        @Override
        public boolean isValid(V value) {
            return value != null && property.getAllowedValues().contains(value);
        }

        @Override
        public Class<V> getType() {
            return property.getValueClass();
        }

        @Override
        public String valueToString(V value) {
            return property.getName(value);
        }
    }
}
