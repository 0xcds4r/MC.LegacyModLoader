package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Container {
    public final List<Slot> inventorySlots = new ArrayList<>();
    public final List<ItemStack> inventoryItemStacks = new ArrayList<>();
    public int windowId;
    private short transactionId;

    protected Slot addSlotToContainer(Slot slot) {
        slot.slotNumber = inventorySlots.size();
        inventorySlots.add(slot);
        inventoryItemStacks.add(ItemStack.EMPTY);
        return slot;
    }

    public List<Slot> getInventory() {
        return inventorySlots;
    }

    public Slot getSlot(int index) {
        return index >= 0 && index < inventorySlots.size() ? inventorySlots.get(index) : null;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return ItemStack.EMPTY;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public void onContainerClosed(EntityPlayer player) {
    }

    public void detectAndSendChanges() {
    }

    public void updateProgressBar(int id, int data) {
    }

    public short getNextTransactionID(net.minecraft.inventory.IInventory inventory) {
        return ++transactionId;
    }
}
