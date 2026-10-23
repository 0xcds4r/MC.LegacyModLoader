package net.minecraft.client;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;

public class Minecraft {
    private static final Minecraft INSTANCE = new Minecraft();
    public EntityPlayerSP player;
    public WorldClient world;
    public FontRenderer fontRenderer;
    public Object currentScreen;
    public int displayWidth = 1920;
    public int displayHeight = 1080;

    private Minecraft() {
    }

    public static Minecraft getMinecraft() {
        return INSTANCE;
    }

    public TextureManager getTextureManager() {
        return new TextureManager();
    }

    public float getRenderPartialTicks() {
        return 1.0F;
    }

    public boolean isSingleplayer() {
        return true;
    }

    public void displayGuiScreen(Object screen) {
        this.currentScreen = screen;
    }
}
