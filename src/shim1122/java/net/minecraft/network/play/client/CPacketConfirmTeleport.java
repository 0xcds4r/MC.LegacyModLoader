package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketConfirmTeleport extends Packet<INetHandler> {
    private final int teleportId;

    public CPacketConfirmTeleport(int teleportId) {
        this.teleportId = teleportId;
    }
}
