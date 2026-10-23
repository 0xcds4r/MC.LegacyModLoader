package net.minecraft.world;

public class WorldServer extends World {
    public WorldProvider provider = new WorldProvider();

    public WorldServer() {
        isRemote = false;
    }

    public WorldProvider getProvider() {
        return provider;
    }
}
