package net.minecraftforge.fluids;

import net.minecraft.item.ItemStack;

public final class FluidUtil {
    private FluidUtil() {
    }

    public static FluidStack getFluidContained(ItemStack container) {
        return null;
    }

    public static ItemStack getFilledBucket(FluidStack fluid) {
        return ItemStack.EMPTY;
    }

    public static boolean interactWithFluidHandler(net.minecraft.entity.player.EntityPlayer player,
                                                   net.minecraft.util.EnumHand hand,
                                                   net.minecraftforge.fluids.capability.IFluidHandler handler) {
        return false;
    }

    public static net.minecraftforge.fluids.capability.IFluidHandler getFluidHandler(
            net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos,
            net.minecraft.util.EnumFacing side) {
        return null;
    }
}
