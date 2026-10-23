package net.minecraft.client.renderer.tileentity;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntitySpecialRenderer<T extends TileEntity> {
    public void render(T tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        render(tileEntity, x, y, z, partialTicks, destroyStage, 1.0F);
    }

    public void render(T tileEntity, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
    }

    public void renderTileEntityAt(T tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        render(tileEntity, x, y, z, partialTicks, destroyStage);
    }
}
