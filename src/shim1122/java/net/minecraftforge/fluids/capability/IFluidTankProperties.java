package net.minecraftforge.fluids.capability;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidTankProperties {
    FluidStack getContents();

    int getCapacity();

    boolean canFill();

    boolean canDrain();

    net.minecraftforge.fluids.Fluid getFluid();
}
