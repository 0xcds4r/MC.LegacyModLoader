package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBush extends Block {
    protected BlockBush(Material material) {
        super(material);
    }

    @Override
    public dev.legacyloader.bridge.LegacyBlockKind legacyKind() {
        return dev.legacyloader.bridge.LegacyBlockKind.BUSH;
    }

    public BlockBush() {
        this(Material.PLANTS);
    }

    public boolean canSustainBush(IBlockState state) {
        return true;
    }

    public boolean canBlockStay(net.minecraft.world.World world, BlockPos pos, IBlockState state) {
        return true;
    }

    public boolean canPlaceBlockAt(net.minecraft.world.World world, BlockPos pos) {
        return true;
    }

    public net.minecraft.util.math.AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new net.minecraft.util.math.AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.8, 0.9);
    }
}
