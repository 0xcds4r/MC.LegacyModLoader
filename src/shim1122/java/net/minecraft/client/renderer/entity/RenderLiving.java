package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public class RenderLiving<T extends EntityLivingBase> extends RenderLivingBase<T> {
    public RenderLiving(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }
}
