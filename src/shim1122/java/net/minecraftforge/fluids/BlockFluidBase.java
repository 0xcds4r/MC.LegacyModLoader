package net.minecraftforge.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFluidBase extends Block {
    protected final Fluid fluid;

    public BlockFluidBase(Fluid fluid, Material material) {
        super(material);
        this.fluid = fluid;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public BlockFluidBase setQuantaPerBlock(int quanta) {
        return this;
    }

    public int getQuantaValue(net.minecraft.world.IBlockAccess world, net.minecraft.util.math.BlockPos pos) {
        return 0;
    }

    public BlockFluidBase setDensity(int density) {
        return this;
    }

    public BlockFluidBase setViscosity(int viscosity) {
        return this;
    }
}
