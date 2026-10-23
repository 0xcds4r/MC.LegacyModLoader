package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketConfirmTransaction extends Packet<INetHandler> {
    private final int windowId;
    private final short uid;
    private final boolean accepted;

    public CPacketConfirmTransaction(int windowId, short uid, boolean accepted) {
        this.windowId = windowId;
        this.uid = uid;
        this.accepted = accepted;
    }

    public int getWindowId() {
        return windowId;
    }

    public short getUid() {
        return uid;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
