package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryCrafting implements IInventory {
    private final ItemStack[] stackList;
    private final int width;
    private final int height;
    private final Container eventHandler;

    public InventoryCrafting(Container eventHandler, int width, int height) {
        this.eventHandler = eventHandler;
        this.width = width;
        this.height = height;
        this.stackList = new ItemStack[width * height];
        for (int i = 0; i < stackList.length; i++) {
            stackList[i] = ItemStack.EMPTY;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ItemStack getStackInRowAndColumn(int row, int column) {
        return row >= 0 && row < width && column >= 0 && column <= height
                ? getStackInSlot(row + column * width) : ItemStack.EMPTY;
    }

    @Override
    public int getSizeInventory() {
        return stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < stackList.length ? stackList[index] : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return getStackInSlot(index).splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        stackList[index] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        stackList[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
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
        java.util.Arrays.fill(stackList, ItemStack.EMPTY);
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
        return new net.minecraft.util.text.TextComponentString("Crafting");
    }
}
