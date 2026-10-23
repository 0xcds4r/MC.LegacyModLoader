package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Slot {
    public final IInventory inventory;
    public final int slotIndex;
    public final int xPos;
    public final int yPos;
    public int slotNumber;

    public Slot(IInventory inventory, int slotIndex, int xPos, int yPos) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public ItemStack getStack() {
        return inventory.getStackInSlot(slotIndex);
    }

    public void putStack(ItemStack stack) {
        inventory.setInventorySlotContents(slotIndex, stack);
        onSlotChanged();
    }

    public void onSlotChanged() {
        inventory.markDirty();
    }

    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(slotIndex, stack);
    }

    public int getSlotStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    public boolean getHasStack() {
        return !getStack().isEmpty();
    }

    public ItemStack decrStackSize(int amount) {
        return inventory.decrStackSize(slotIndex, amount);
    }

    public boolean canTakeStack(EntityPlayer player) {
        return true;
    }

    public int getSlotIndex() {
        return slotIndex;
    }
}
