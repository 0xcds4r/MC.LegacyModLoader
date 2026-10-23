package net.minecraft.block.properties;

import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.Collection;

public class PropertyDirection extends PropertyEnum<EnumFacing> {
    private PropertyDirection(String name, Collection<EnumFacing> values) {
        super(name, EnumFacing.class, values);
    }

    public static PropertyDirection create(String name) {
        return new PropertyDirection(name, Arrays.asList(EnumFacing.values()));
    }

    public static PropertyDirection create(String name, Collection<EnumFacing> values) {
        return new PropertyDirection(name, values);
    }

    public static PropertyDirection create(String name, EnumFacing... values) {
        return new PropertyDirection(name, Arrays.asList(values));
    }

    public static PropertyDirection create(String name, com.google.common.base.Predicate<EnumFacing> filter) {
        return new PropertyDirection(name,
                Arrays.stream(EnumFacing.values()).filter(filter::apply).toList());
    }
}
