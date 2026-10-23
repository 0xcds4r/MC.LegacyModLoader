package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketKeepAlive extends Packet<INetHandler> {
    private final long key;

    public CPacketKeepAlive() {
        this(0L);
    }

    public CPacketKeepAlive(long key) {
        this.key = key;
    }

    public long getKey() {
        return key;
    }
}
