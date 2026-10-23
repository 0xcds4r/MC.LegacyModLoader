package net.minecraft.world;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class BossInfo {
    private ITextComponent name;
    private float percent;
    private Color color = Color.PURPLE;
    private Overlay overlay = Overlay.PROGRESS;
    private boolean darkenSky;
    private boolean playEndBossMusic;
    private boolean createFog;
    private boolean visible = true;

    public BossInfo(ITextComponent name, Color color, Overlay overlay) {
        this.name = name == null ? new TextComponentString("") : name;
        this.color = color;
        this.overlay = overlay;
    }

    public ITextComponent getName() {
        return name;
    }

    public void setName(ITextComponent name) {
        this.name = name;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public boolean isDarkenSky() {
        return darkenSky;
    }

    public BossInfo setDarkenSky(boolean darkenSky) {
        this.darkenSky = darkenSky;
        return this;
    }

    public boolean isPlayEndBossMusic() {
        return playEndBossMusic;
    }

    public BossInfo setPlayEndBossMusic(boolean playEndBossMusic) {
        this.playEndBossMusic = playEndBossMusic;
        return this;
    }

    public boolean isCreateFog() {
        return createFog;
    }

    public BossInfo setCreateFog(boolean createFog) {
        this.createFog = createFog;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public enum Color {
        PINK,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        WHITE
    }

    public enum Overlay {
        PROGRESS,
        NOTCHED_6,
        NOTCHED_10,
        NOTCHED_12,
        NOTCHED_20
    }
}
