package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.monster.EntityZombie;

public class RenderZombie extends RenderLivingBase<EntityZombie> {
    public RenderZombie(RenderManager renderManager) {
        super(new ModelBiped(), 0.5F);
    }
}
