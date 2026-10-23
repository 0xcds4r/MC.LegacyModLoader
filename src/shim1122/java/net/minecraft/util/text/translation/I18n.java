package net.minecraft.util.text.translation;

public class I18n {
    private I18n() {
    }

    public static String translateToLocal(String key) {
        return key;
    }

    public static String translateToLocalFormatted(String key, Object... format) {
        String value = translateToLocal(key);
        try {
            return String.format(value.replace("%s", "%s").replace("%d", "%d"), format);
        } catch (RuntimeException e) {
            return value;
        }
    }

    public static boolean canTranslate(String key) {
        return false;
    }
}
