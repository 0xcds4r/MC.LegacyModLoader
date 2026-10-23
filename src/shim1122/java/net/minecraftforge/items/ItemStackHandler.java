package net.minecraftforge.items;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackHandler implements IItemHandler {
    protected final List<ItemStack> stacks;
    protected int slotLimit = 64;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int size) {
        stacks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            stacks.add(ItemStack.EMPTY);
        }
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot >= 0 && slot < stacks.size() ? stacks.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        if (!simulate) {
            stacks.set(slot, stack);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stack = getStackInSlot(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = stack.copy();
        if (!simulate) {
            stacks.set(slot, ItemStack.EMPTY);
        }
        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotLimit;
    }

    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    public void setSize(int size) {
        while (stacks.size() < size) {
            stacks.add(ItemStack.EMPTY);
        }
        while (stacks.size() > size) {
            stacks.remove(stacks.size() - 1);
        }
    }
}
