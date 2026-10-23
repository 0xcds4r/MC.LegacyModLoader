package baubles.api;

/**
 * Baubles 1.5.x equipment slot types, as used by 1.12.2 mods through the public
 * API. The loader ships an API-compatible shim so dependents can resolve types
 * without a real Baubles jar. Equipment behaviour is not implemented yet.
 */
public enum BaubleType {
    AMULET,
    RING,
    BELT,
    TRINKET,
    HEAD,
    BODY,
    CHARM;

    public int[] getValidSlots() {
        return switch (this) {
            case AMULET -> new int[]{0};
            case RING -> new int[]{1, 2};
            case BELT -> new int[]{3};
            case TRINKET -> new int[]{4, 5};
            case HEAD -> new int[]{6};
            case BODY -> new int[]{7};
            case CHARM -> new int[]{8};
        };
    }

    public String getLabel() {
        return "bauble." + name().toLowerCase();
    }
}
