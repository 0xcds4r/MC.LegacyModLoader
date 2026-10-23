package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketEntityAction extends Packet<INetHandler> {
    private final int entityId;
    private final Action action;
    private final int auxData;

    public CPacketEntityAction(net.minecraft.entity.Entity entity, Action action) {
        this(entity, action, 0);
    }

    public CPacketEntityAction(net.minecraft.entity.Entity entity, Action action, int auxData) {
        this.entityId = entity.getEntityId();
        this.action = action;
        this.auxData = auxData;
    }

    public Action getAction() {
        return action;
    }

    public enum Action {
        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING
    }
}
