package net.minecraft.entity.ai.attributes;

public class RangedAttribute extends BaseAttribute {
    private final double minimumValue;
    private final double maximumValue;

    public RangedAttribute(String name, double defaultValue, double minimumValue, double maximumValue) {
        super(name, defaultValue);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    public RangedAttribute(IAttribute parent, String name, double defaultValue,
                           double minimumValue, double maximumValue) {
        this(name, defaultValue, minimumValue, maximumValue);
    }

    public double getMinimumValue() {
        return minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

    public RangedAttribute setDescription(String description) {
        return this;
    }

    @Override
    public double clampValue(double value) {
        return Math.max(minimumValue, Math.min(maximumValue, value));
    }
}
