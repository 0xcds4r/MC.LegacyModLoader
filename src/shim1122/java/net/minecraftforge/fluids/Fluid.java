package net.minecraftforge.fluids;

public class Fluid {
    private final String name;
    private int temperature = 300;
    private int luminosity;
    private int density = 1000;
    private int viscosity = 1000;

    public Fluid(String name) {
        this.name = name;
    }

    public Fluid(String name, net.minecraft.util.ResourceLocation still,
                 net.minecraft.util.ResourceLocation flowing) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Fluid setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public Fluid setLuminosity(int luminosity) {
        this.luminosity = luminosity;
        return this;
    }

    public Fluid setDensity(int density) {
        this.density = density;
        return this;
    }

    public Fluid setViscosity(int viscosity) {
        this.viscosity = viscosity;
        return this;
    }

    public Fluid setRarity(net.minecraft.item.EnumRarity rarity) {
        return this;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getLuminosity() {
        return luminosity;
    }

    public int getDensity() {
        return density;
    }

    public int getViscosity() {
        return viscosity;
    }
}
