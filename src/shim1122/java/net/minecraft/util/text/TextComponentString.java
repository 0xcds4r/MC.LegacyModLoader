package net.minecraft.util.text;

public class TextComponentString implements ITextComponent {
    private final String text;

    public TextComponentString(String text) {
        this.text = text == null ? "" : text;
    }

    @Override
    public String getUnformattedText() {
        return text;
    }

    @Override
    public String getFormattedText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
