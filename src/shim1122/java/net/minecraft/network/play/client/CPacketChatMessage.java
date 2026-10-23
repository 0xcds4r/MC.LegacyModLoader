package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketChatMessage extends Packet<INetHandler> {
    private final String message;

    public CPacketChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
