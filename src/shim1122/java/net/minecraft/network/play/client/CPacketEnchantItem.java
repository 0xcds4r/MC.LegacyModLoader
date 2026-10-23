package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketEnchantItem extends Packet<INetHandler> {
    private final int windowId;
    private final int button;

    public CPacketEnchantItem(int windowId, int button) {
        this.windowId = windowId;
        this.button = button;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getButton() {
        return button;
    }
}
