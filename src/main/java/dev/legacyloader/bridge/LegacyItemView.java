package dev.legacyloader.bridge;

/**
 * Read-only projection of a legacy {@code net.minecraft.item.Item}.
 */
public interface LegacyItemView {
    String legacyTranslationKey();

    default String legacyTranslationKey(int metadata) {
        return legacyTranslationKey();
    }

    String legacyRegistryPath();

    int legacyMaxStackSize();

    int legacyMaxDamage();

    boolean legacyHasSubtypes();

    boolean legacyIsFood();

    default int legacyFoodNutrition() {
        return 4;
    }

    default float legacyFoodSaturation() {
        return 0.6F;
    }

    default boolean legacyAlwaysEdible() {
        return false;
    }

    default LegacyToolKind legacyToolKind() {
        return LegacyToolKind.NONE;
    }

    default String legacyToolMaterial() {
        return "IRON";
    }

    default float legacyAttackDamage() {
        return 0.0F;
    }

    default float legacyAttackSpeed() {
        return -2.4F;
    }

    default LegacyArmorSlot legacyArmorSlot() {
        return null;
    }

    default String legacyArmorMaterial() {
        return "IRON";
    }

    /**
     * Non-null when this item is a legacy {@code ItemBlock}; the bridge then
     * registers a real modern block item instead of a plain item.
     */
    LegacyBlockView legacyBlock();
}
