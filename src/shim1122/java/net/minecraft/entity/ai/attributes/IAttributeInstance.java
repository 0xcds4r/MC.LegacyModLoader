package net.minecraft.entity.ai.attributes;

public interface IAttributeInstance {
    IAttribute getAttribute();

    double getBaseValue();

    void setBaseValue(double value);

    double getAttributeValue();

    void applyModifier(AttributeModifier modifier);

    void removeModifier(AttributeModifier modifier);
}
