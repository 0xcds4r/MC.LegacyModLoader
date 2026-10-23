package net.minecraft.block.properties;

import java.util.Collection;
import java.util.Collections;

public abstract class Property<T extends Comparable<T>> implements IProperty<T> {
    private final String name;
    private final Class<T> valueClass;

    protected Property(String name, Class<T> valueClass) {
        this.name = name;
        this.valueClass = valueClass;
    }

    public String getName() {
        return name;
    }

    public Class<T> getValueClass() {
        return valueClass;
    }

    public Collection<T> getAllowedValues() {
        return Collections.emptyList();
    }

    public String getName(T value) {
        return String.valueOf(value);
    }

    @Override
    public T parseValue(String value) {
        return null;
    }
}
