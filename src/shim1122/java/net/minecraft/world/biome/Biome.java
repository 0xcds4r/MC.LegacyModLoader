package net.minecraft.world.biome;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Biome implements IForgeRegistryEntry<Biome> {
    private ResourceLocation registryName;
    private String biomeName = "plains";
    private float temperature = 0.5F;
    private float rainfall = 0.5F;
    private float baseHeight = 0.1F;
    private float heightVariation = 0.2F;
    private boolean enableSnow;

    public Biome setBiomeName(String name) {
        this.biomeName = name;
        return this;
    }

    public String getBiomeName() {
        return biomeName;
    }

    public float getTemperature() {
        return temperature;
    }

    public Biome setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public float getRainfall() {
        return rainfall;
    }

    public Biome setRainfall(float rainfall) {
        this.rainfall = rainfall;
        return this;
    }

    public float getBaseHeight() {
        return baseHeight;
    }

    public float getHeightVariation() {
        return heightVariation;
    }

    public Biome setHeightVariation(float variation) {
        this.heightVariation = variation;
        return this;
    }

    public void setEnableSnow() {
        this.enableSnow = true;
    }

    public boolean getEnableSnow() {
        return enableSnow;
    }

    @Override
    public Biome setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public Class<Biome> getRegistrySuperType() {
        return Biome.class;
    }

    public static class BiomeProperties {
        private final String name;

        public BiomeProperties(String name) {
            this.name = name;
        }

        public BiomeProperties setTemperature(float temperature) {
            return this;
        }

        public BiomeProperties setRainfall(float rainfall) {
            return this;
        }

        public BiomeProperties setBaseHeight(float height) {
            return this;
        }

        public BiomeProperties setHeightVariation(float variation) {
            return this;
        }
    }
}
