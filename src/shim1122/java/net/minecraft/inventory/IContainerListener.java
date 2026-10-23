package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

public interface IContainerListener {
    void sendSlotContents(Container container, int slot, ItemStack stack);

    void sendWindowProperty(Container container, int varToUpdate, int newValue);

    void sendAllWindowProperties(Container container, IInventory inventory);
}
