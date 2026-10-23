package net.minecraft.client.gui;

public class FontRenderer {
    public int drawString(String text, int x, int y, int color) {
        return x + getStringWidth(text);
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        return (int) x + getStringWidth(text);
    }

    public int getStringWidth(String text) {
        return text == null ? 0 : text.length() * 6;
    }

    public int getCharWidth(char character) {
        return 6;
    }

    public void drawSplitString(String text, int x, int y, int width, int color) {
    }

    public java.util.List<String> listFormattedStringToWidth(String text, int width) {
        return java.util.List.of(text);
    }
}
