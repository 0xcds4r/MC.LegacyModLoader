package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.entity.monster.EntitySlime;

public class RenderSlime extends RenderLivingBase<EntitySlime> {
    public RenderSlime(RenderManager renderManager) {
        super(new ModelSlime(16), 0.25F);
    }
}
