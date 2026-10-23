package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketHeldItemChange extends Packet<INetHandler> {
    private final int slotId;

    public CPacketHeldItemChange(int slotId) {
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
    }
}
