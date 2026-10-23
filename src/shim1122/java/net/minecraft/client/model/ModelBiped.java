package net.minecraft.client.model;

public class ModelBiped extends ModelBase {
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public int heldItemLeft;
    public int heldItemRight;
    public boolean isSneak;
    public boolean aimedBow;

    public ModelBiped() {
        this(0.0F);
    }

    public ModelBiped(float modelSize) {
        this(modelSize, 0.0F, 64, 32);
    }

    public ModelBiped(float modelSize, float yOffset, int textureWidth, int textureHeight) {
        bipedHead = new ModelRenderer(this);
        bipedHeadwear = new ModelRenderer(this);
        bipedBody = new ModelRenderer(this);
        bipedRightArm = new ModelRenderer(this);
        bipedLeftArm = new ModelRenderer(this);
        bipedRightLeg = new ModelRenderer(this);
        bipedLeftLeg = new ModelRenderer(this);
    }
}
