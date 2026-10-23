package net.minecraft.block.properties;

import java.util.List;

public class PropertyEnum<T extends Enum<T> & Comparable<T>> extends Property<T> {
    private final List<T> values;

    private PropertyEnum(String name, Class<T> enumClass) {
        this(name, enumClass, List.of(enumClass.getEnumConstants()));
    }

    protected PropertyEnum(String name, Class<T> enumClass, java.util.Collection<T> values) {
        super(name, enumClass);
        this.values = List.copyOf(values);
    }

    public static <T extends Enum<T> & Comparable<T>> PropertyEnum<T> create(String name, Class<T> enumClass) {
        return new PropertyEnum<>(name, enumClass);
    }

    @Override
    public List<T> getAllowedValues() {
        return values;
    }

    @Override
    public T parseValue(String value) {
        for (T constant : values) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        return null;
    }
}
