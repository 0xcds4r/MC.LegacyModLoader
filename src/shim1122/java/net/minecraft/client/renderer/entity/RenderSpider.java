package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.entity.monster.EntitySpider;

public class RenderSpider extends RenderLiving<EntitySpider> {
    public RenderSpider(RenderManager renderManager) {
        super(new ModelSpider(), 1.0F);
    }
}
