package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketCloseWindow extends Packet<INetHandler> {
    private final int windowId;

    public CPacketCloseWindow(int windowId) {
        this.windowId = windowId;
    }

    public int getWindowId() {
        return windowId;
    }
}
