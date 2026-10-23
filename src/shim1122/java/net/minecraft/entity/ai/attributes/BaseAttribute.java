package net.minecraft.entity.ai.attributes;

public class BaseAttribute implements IAttribute {
    private final String name;
    private final double defaultValue;

    public BaseAttribute(String name, double defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double clampValue(double value) {
        return value;
    }

    @Override
    public double getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean getShouldWatch() {
        return false;
    }

    public BaseAttribute setShouldWatch(boolean shouldWatch) {
        return this;
    }
}
