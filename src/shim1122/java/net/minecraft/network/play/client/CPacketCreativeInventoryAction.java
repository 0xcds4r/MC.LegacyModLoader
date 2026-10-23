package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.item.ItemStack;

public class CPacketCreativeInventoryAction extends Packet<INetHandler> {
    private final int slotId;
    private final ItemStack stack;

    public CPacketCreativeInventoryAction(int slotId, ItemStack stack) {
        this.slotId = slotId;
        this.stack = stack;
    }

    public int getSlotId() {
        return slotId;
    }

    public ItemStack getStack() {
        return stack;
    }
}
