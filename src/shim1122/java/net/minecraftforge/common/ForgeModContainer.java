package net.minecraftforge.common;

import net.minecraftforge.fluids.UniversalBucket;

public class ForgeModContainer {
    public static final ForgeModContainer instance = new ForgeModContainer();
    public UniversalBucket universalBucket;

    public static ForgeModContainer getInstance() {
        return instance;
    }

    public String getVersion() {
        return "14.23.5.2768";
    }

    public String getModId() {
        return "forge";
    }

    public String getName() {
        return "Forge";
    }
}
