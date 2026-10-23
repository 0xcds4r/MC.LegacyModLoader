package net.minecraft.block.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PropertyInteger extends Property<Integer> {
    private final int min;
    private final int max;

    private PropertyInteger(String name, int min, int max) {
        super(name, Integer.class);
        this.min = min;
        this.max = max;
    }

    public static PropertyInteger create(String name, int min, int max) {
        return new PropertyInteger(name, min, max);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public Collection<Integer> getAllowedValues() {
        List<Integer> values = new ArrayList<>();
        for (int value = min; value <= max; value++) {
            values.add(value);
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public Integer parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
