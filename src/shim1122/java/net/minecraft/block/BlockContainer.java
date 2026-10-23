package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
    protected BlockContainer(Material material) {
        super(material);
        this.hasTileEntity = true;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);
}
