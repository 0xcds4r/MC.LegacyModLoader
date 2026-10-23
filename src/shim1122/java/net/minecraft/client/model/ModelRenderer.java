package net.minecraft.client.model;

public class ModelRenderer {
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public boolean showModel = true;
    public boolean isHidden;

    public ModelRenderer(ModelBase model) {
    }

    public ModelRenderer(ModelBase model, int textureOffsetX, int textureOffsetY) {
    }

    public ModelRenderer setTextureOffset(int x, int y) {
        return this;
    }

    public ModelRenderer addBox(String partName, float x, float y, float z, int width, int height, int depth) {
        return this;
    }

    public ModelRenderer addBox(float x, float y, float z, int width, int height, int depth) {
        return this;
    }

    public ModelRenderer addBox(float x, float y, float z, int width, int height, int depth, float delta) {
        return this;
    }

    public void setRotationPoint(float x, float y, float z) {
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.rotationPointZ = z;
    }

    public void render(float scale) {
    }

    public void setTextureSize(int width, int height) {
    }
}
