package net.minecraftforge.fluids;

import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidTank implements IFluidHandler, IFluidTank {
    protected FluidStack fluid;
    protected int capacity;

    public FluidTank(int capacity) {
        this(null, capacity);
    }

    public FluidTank(FluidStack fluid, int capacity) {
        this.fluid = fluid;
        this.capacity = capacity;
    }

    public FluidTank(Fluid fluid, int amount, int capacity) {
        this(new FluidStack(fluid, amount), capacity);
    }

    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        return fluid == null ? 0 : fluid.getAmount();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null) {
            return 0;
        }
        int accepted = Math.min(capacity - getFluidAmount(), resource.getAmount());
        if (doFill && accepted > 0) {
            if (fluid == null) {
                fluid = new FluidStack(resource.getFluid(), accepted);
            } else {
                fluid.setAmount(fluid.getAmount() + accepted);
            }
        }
        return accepted;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return resource == null ? null : drain(resource.getAmount(), doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid == null) {
            return null;
        }
        int drained = Math.min(maxDrain, fluid.getAmount());
        FluidStack result = new FluidStack(fluid.getFluid(), drained);
        if (doDrain) {
            fluid.setAmount(fluid.getAmount() - drained);
            if (fluid.getAmount() <= 0) {
                fluid = null;
            }
        }
        return result;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }
}
