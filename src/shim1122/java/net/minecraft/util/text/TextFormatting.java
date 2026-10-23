package net.minecraft.util.text;

public enum TextFormatting {
    BLACK,
    DARK_BLUE,
    DARK_GREEN,
    DARK_AQUA,
    DARK_RED,
    DARK_PURPLE,
    GOLD,
    GRAY,
    DARK_GRAY,
    BLUE,
    GREEN,
    AQUA,
    RED,
    LIGHT_PURPLE,
    YELLOW,
    WHITE,
    OBFUSCATED,
    BOLD,
    STRIKETHROUGH,
    UNDERLINE,
    ITALIC,
    RESET;

    public String getFriendlyName() {
        return name().toLowerCase();
    }

    public int getColorIndex() {
        return ordinal();
    }

    public static TextFormatting fromColorIndex(int index) {
        TextFormatting[] values = values();
        return index >= 0 && index < values.length ? values[index] : RESET;
    }

    @Override
    public String toString() {
        return "\u00a7" + Integer.toHexString(ordinal());
    }
}
