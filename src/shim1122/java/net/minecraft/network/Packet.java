package net.minecraft.network;

import io.netty.buffer.ByteBuf;

public class Packet<T extends INetHandler> {
    public void readPacketData(ByteBuf data) {
    }

    public void writePacketData(ByteBuf data) {
    }

    public void processPacket(T handler) {
    }

    public boolean isWriteable() {
        return true;
    }
}
