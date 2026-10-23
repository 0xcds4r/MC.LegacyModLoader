package net.minecraft.network.play.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketCustomPayload extends Packet<INetHandler> {
    private final String channel;
    private final ByteBuf data;

    public CPacketCustomPayload(String channel, ByteBuf data) {
        this.channel = channel;
        this.data = data;
    }

    public String getChannelName() {
        return channel;
    }

    public ByteBuf getBufferData() {
        return data;
    }
}
