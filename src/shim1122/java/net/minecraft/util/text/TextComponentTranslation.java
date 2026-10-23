package net.minecraft.util.text;

import net.minecraft.util.text.translation.I18n;

public class TextComponentTranslation implements ITextComponent {
    private final String key;
    private final Object[] formatArgs;

    public TextComponentTranslation(String translationKey, Object... formatArgs) {
        this.key = translationKey;
        this.formatArgs = formatArgs;
    }

    public String getKey() {
        return key;
    }

    public Object[] getFormatArgs() {
        return formatArgs;
    }

    @Override
    public String getUnformattedText() {
        return I18n.translateToLocalFormatted(key, formatArgs);
    }

    @Override
    public String getFormattedText() {
        return getUnformattedText();
    }

    @Override
    public String toString() {
        return getUnformattedText();
    }
}
