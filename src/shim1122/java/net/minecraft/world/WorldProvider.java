package net.minecraft.world;

public class WorldProvider {
    public int getDimension() {
        return 0;
    }

    public String getDimensionType() {
        return "overworld";
    }

    public boolean canRespawnHere() {
        return true;
    }

    public boolean isSurfaceWorld() {
        return true;
    }
}
