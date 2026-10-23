package net.minecraft.item;

public enum EnumDyeColor {
    WHITE(0, 0xFFFFFF),
    ORANGE(1, 0xEF7D00),
    MAGENTA(2, 0xBD44B3),
    LIGHT_BLUE(3, 0x3AB3DA),
    YELLOW(4, 0xFED83D),
    LIME(5, 0x80C71F),
    PINK(6, 0xF38BAA),
    GRAY(7, 0x474F52),
    SILVER(8, 0x9D9D97),
    CYAN(9, 0x169C9C),
    PURPLE(10, 0x8932B8),
    BLUE(11, 0x3C44AA),
    BROWN(12, 0x835432),
    GREEN(13, 0x5E7C16),
    RED(14, 0xB02E26),
    BLACK(15, 0x1D1D21);

    private final int dyeDamage;
    private final int colorValue;

    EnumDyeColor(int dyeDamage, int colorValue) {
        this.dyeDamage = dyeDamage;
        this.colorValue = colorValue;
    }

    public int getDyeDamage() {
        return dyeDamage;
    }

    public int getColorValue() {
        return colorValue;
    }

    public static String getTranslationKey(int meta) {
        return "item.dye." + byDyeDamage(meta).name().toLowerCase();
    }

    public String getTranslationKey() {
        return "item.dye." + name().toLowerCase();
    }

    public static EnumDyeColor byDyeDamage(int damage) {
        EnumDyeColor[] values = values();
        return damage >= 0 && damage < values.length ? values[damage] : WHITE;
    }
}
