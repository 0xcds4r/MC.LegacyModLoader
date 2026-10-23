package net.minecraft.network.play.server;

import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class SPacketSetSlot extends Packet<INetHandler> {
    private int windowId;
    private int slot;
    private ItemStack item;

    public SPacketSetSlot() {
    }

    public SPacketSetSlot(int windowId, int slot, ItemStack item) {
        this.windowId = windowId;
        this.slot = slot;
        this.item = item;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getStack() {
        return item;
    }
}
