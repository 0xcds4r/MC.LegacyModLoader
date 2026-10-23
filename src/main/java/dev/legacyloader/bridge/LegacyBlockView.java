package dev.legacyloader.bridge;

/**
 * Read-only projection of a legacy {@code net.minecraft.block.Block}.
 * Implemented by the 1.12.2 shim, consumed by the modern block adapter.
 */
public interface LegacyBlockView {
    String legacyTranslationKey();

    String legacyRegistryPath();

    float legacyHardness();

    float legacyResistance();

    /** 0..15 */
    int legacyLightLevel();

    float legacyFriction();

    boolean legacyOpaqueCube();

    boolean legacyFullCube();

    boolean legacyHasTileEntity();

    String legacyHarvestTool();

    int legacyHarvestLevel();

    default LegacyBlockKind legacyKind() {
        return LegacyBlockKind.BLOCK;
    }
}
