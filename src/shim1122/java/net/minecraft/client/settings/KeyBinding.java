package net.minecraft.client.settings;

public class KeyBinding {
    private final String description;
    private final int keyCode;
    private final String category;

    public KeyBinding(String description, int keyCode, String category) {
        this.description = description;
        this.keyCode = keyCode;
        this.category = category;
    }

    public boolean isKeyDown() {
        return false;
    }

    public boolean isPressed() {
        return false;
    }

    public String getKeyDescription() {
        return description;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public String getKeyCategory() {
        return category;
    }
}
