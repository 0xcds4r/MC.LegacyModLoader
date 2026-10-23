package net.minecraft.client.gui;

public class GuiScreen {
    protected int width;
    protected int height;
    protected int mouseX;
    protected int mouseY;

    public void initGui() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public void updateScreen() {
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void onGuiClosed() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
