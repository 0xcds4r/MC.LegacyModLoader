package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public abstract class RenderLivingBase<T extends EntityLivingBase> extends Render<T> {
    protected ModelBase mainModel;

    public RenderLivingBase(ModelBase modelBase, float shadowSize) {
        this.mainModel = modelBase;
    }

    public ModelBase getMainModel() {
        return mainModel;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return super.getEntityTexture(entity);
    }
}
