package net.minecraftforge.fml.common;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IFuelHandler {
    int getBurnTime(ItemStack fuel);
}
