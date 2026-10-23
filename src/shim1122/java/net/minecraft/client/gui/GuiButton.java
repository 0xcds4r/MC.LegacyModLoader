package net.minecraft.client.gui;

public class GuiButton extends GuiScreen {
    public int x;
    public int y;
    public int width;
    public int height;
    public int id;
    public boolean enabled = true;
    public boolean visible = true;
    public String displayString = "";

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public void drawButton(net.minecraft.client.Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    }

    public boolean mousePressed(net.minecraft.client.Minecraft mc, int mouseX, int mouseY) {
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }
}
