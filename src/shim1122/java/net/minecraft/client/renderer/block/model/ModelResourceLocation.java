package net.minecraft.client.renderer.block.model;

import net.minecraft.util.ResourceLocation;

public class ModelResourceLocation extends ResourceLocation {
    public ModelResourceLocation(String location) {
        super(location);
    }

    public ModelResourceLocation(String namespace, String path, String variant) {
        super(namespace, path);
    }

    public ModelResourceLocation(ResourceLocation location, String variant) {
        super(location.getNamespace(), location.getPath());
    }
}
