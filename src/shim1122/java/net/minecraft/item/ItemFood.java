package net.minecraft.item;

/**
 * Basic food shim. Saturation and potion effects are recorded but not yet
 * translated into modern food components.
 */
public class ItemFood extends Item {
    private final int healAmount;
    private final float saturationModifier;
    private final boolean alwaysEdible;

    public ItemFood(int amount, float saturation, boolean isWolfFood) {
        this.healAmount = amount;
        this.saturationModifier = saturation;
        this.alwaysEdible = isWolfFood;
        setMaxStackSize(64);
    }

    public ItemFood(int amount, boolean isWolfFood) {
        this(amount, 0.6F, isWolfFood);
    }

    public int getHealAmount(ItemStack stack) {
        return healAmount;
    }

    public float getSaturationModifier(ItemStack stack) {
        return saturationModifier;
    }

    public ItemFood setAlwaysEdible() {
        return this;
    }

    public ItemFood setPotionEffect(net.minecraft.potion.PotionEffect effect, float probability) {
        return this;
    }

    @Override
    public boolean legacyIsFood() {
        return true;
    }

    @Override
    public int legacyFoodNutrition() {
        return healAmount;
    }

    @Override
    public float legacyFoodSaturation() {
        return saturationModifier;
    }

    @Override
    public boolean legacyAlwaysEdible() {
        return alwaysEdible;
    }
}
