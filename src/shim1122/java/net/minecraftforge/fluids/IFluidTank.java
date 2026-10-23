package net.minecraftforge.fluids;

public interface IFluidTank {
    FluidStack getFluid();

    int getFluidAmount();

    int getCapacity();

    int fill(FluidStack resource, boolean doFill);

    FluidStack drain(FluidStack resource, boolean doDrain);

    FluidStack drain(int maxDrain, boolean doDrain);
}
