package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketUseEntity extends Packet<INetHandler> {
    private int entityId;
    private Action action;

    public CPacketUseEntity() {
    }

    public CPacketUseEntity(Entity entity) {
        this.entityId = entity.getEntityId();
        this.action = Action.INTERACT;
    }

    public CPacketUseEntity(Entity entity, Action action) {
        this.entityId = entity.getEntityId();
        this.action = action;
    }

    public int getEntityId() {
        return entityId;
    }

    public Action getAction() {
        return action;
    }

    public enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }
}
