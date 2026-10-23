package net.minecraft.block;

/**
 * 1.12.2 sound type constant holder. Only names are exposed; the modern
 * adapter currently uses vanilla defaults.
 */
public class SoundType {
    public static final SoundType STONE = new SoundType("stone");
    public static final SoundType WOOD = new SoundType("wood");
    public static final SoundType GROUND = new SoundType("ground");
    public static final SoundType PLANT = new SoundType("plant");
    public static final SoundType SAND = new SoundType("sand");
    public static final SoundType SNOW = new SoundType("snow");
    public static final SoundType GLASS = new SoundType("glass");
    public static final SoundType METAL = new SoundType("metal");
    public static final SoundType CLOTH = new SoundType("cloth");
    public static final SoundType LADDER = new SoundType("ladder");
    public static final SoundType ANVIL = new SoundType("anvil");
    public static final SoundType SLIME = new SoundType("slime");

    private final String name;

    public SoundType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
