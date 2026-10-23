package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory {
    private final List<ItemStack> stacks = new ArrayList<>(27);
    public int numPlayersUsing;
    private String customName = "";

    public TileEntityChest() {
        for (int i = 0; i < 27; i++) {
            stacks.add(ItemStack.EMPTY);
        }
    }

    @Override
    public int getSizeInventory() {
        return stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return stacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return stacks.get(index).splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = stacks.get(index);
        stacks.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        stacks.set(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        numPlayersUsing++;
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        numPlayersUsing = Math.max(0, numPlayersUsing - 1);
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
        return "container.chest";
    }

    @Override
    public boolean hasCustomName() {
        return !customName.isEmpty();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Chest");
    }
}
