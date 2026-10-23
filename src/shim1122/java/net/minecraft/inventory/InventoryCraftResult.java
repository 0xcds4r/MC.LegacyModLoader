package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

public class InventoryCraftResult implements IInventory {
    private ItemStack stack = ItemStack.EMPTY;

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack result = stack.splitStack(count);
        stack = result;
        return result;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack result = stack;
        stack = ItemStack.EMPTY;
        return result;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(net.minecraft.entity.player.EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(net.minecraft.entity.player.EntityPlayer player) {
    }

    @Override
    public void closeInventory(net.minecraft.entity.player.EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        stack = ItemStack.EMPTY;
    }

    @Override
    public String getName() {
        return "container.crafting";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public net.minecraft.util.text.ITextComponent getDisplayName() {
        return new net.minecraft.util.text.TextComponentString("Result");
    }
}
