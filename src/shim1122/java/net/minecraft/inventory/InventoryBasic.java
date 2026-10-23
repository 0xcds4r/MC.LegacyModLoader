package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class InventoryBasic implements IInventory {
    private final String name;
    private final List<ItemStack> stacks;
    private int stackLimit = 64;
    private IInventoryChangedListener listener;

    public InventoryBasic(String name, boolean customName, int size) {
        this.name = name == null ? "" : name;
        this.stacks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            stacks.add(ItemStack.EMPTY);
        }
    }

    public InventoryBasic(String name, int size) {
        this(name, false, size);
    }

    @Override
    public int getSizeInventory() {
        return stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < stacks.size() ? stacks.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = getStackInSlot(index);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return stack.splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        stacks.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        stacks.set(index, stack);
        if (listener != null) {
            listener.onInventoryChanged(this);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    public void setInventoryStackLimit(int limit) {
        this.stackLimit = limit;
    }

    public void addInventoryChangeListener(IInventoryChangedListener listener) {
        this.listener = listener;
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
        stacks.replaceAll(ignored -> ItemStack.EMPTY);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(name);
    }
}
