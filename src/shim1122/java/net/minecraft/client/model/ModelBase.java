package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public abstract class ModelBase {
    public boolean isRiding;
    public boolean isChild;

    public void render(Entity entity, float limbSwing, float limbSwingAmount,
                       float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch, float scale, Entity entity) {
    }
}
