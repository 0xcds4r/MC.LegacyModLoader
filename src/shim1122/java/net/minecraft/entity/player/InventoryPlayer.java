package net.minecraft.entity.player;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryPlayer {
    public final List<ItemStack> mainInventory = new ArrayList<>();
    public int currentItem;
    public ItemStack getCurrentItem;

    public InventoryPlayer() {
    }

    public ItemStack getCurrentItem() {
        return getCurrentItem;
    }

    public ItemStack getStackInSlot(int slot) {
        return slot >= 0 && slot < mainInventory.size() ? mainInventory.get(slot) : ItemStack.EMPTY;
    }

    public int getFirstEmptyStack() {
        for (int i = 0; i < mainInventory.size(); i++) {
            if (mainInventory.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        while (mainInventory.size() <= slot) {
            mainInventory.add(ItemStack.EMPTY);
        }
        mainInventory.set(slot, stack);
    }

    public void addItemStackToInventory(ItemStack stack) {
        mainInventory.add(stack);
    }
}
