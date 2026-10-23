package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;

public class RenderBiped<T extends EntityLivingBase> extends RenderLiving<T> {
    protected ModelBiped modelBipedMain;

    public RenderBiped(RenderManager renderManager, ModelBiped model, float shadowSize) {
        super(model, shadowSize);
        this.modelBipedMain = model;
    }
}
