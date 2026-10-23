package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketClientStatus extends Packet<INetHandler> {
    private final Action action;

    public CPacketClientStatus(Action action) {
        this.action = action;
    }

    public Action getStatus() {
        return action;
    }

    public enum Action {
        PERFORM_RESPAWN,
        REQUEST_STATS
    }
}
