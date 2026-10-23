package net.minecraft.block.properties;

import java.util.List;

public class PropertyBool extends Property<Boolean> {
    private PropertyBool(String name) {
        super(name, Boolean.class);
    }

    public static PropertyBool create(String name) {
        return new PropertyBool(name);
    }

    @Override
    public List<Boolean> getAllowedValues() {
        return List.of(Boolean.TRUE, Boolean.FALSE);
    }

    @Override
    public Boolean parseValue(String value) {
        return Boolean.parseBoolean(value);
    }
}
