package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
    ItemStack dispense(IBlockSource source, ItemStack stack);
}
