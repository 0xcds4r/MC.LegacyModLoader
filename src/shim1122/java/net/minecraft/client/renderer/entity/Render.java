package net.minecraft.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class Render<T extends Entity> {
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    }

    protected void bindTexture(ResourceLocation location) {
    }

    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation("minecraft", "textures/misc/white.png");
    }
}
