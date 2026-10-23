package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class SPacketAnimation extends Packet<INetHandler> {
    private int entityId;
    private int animationType;

    public SPacketAnimation() {
    }

    public SPacketAnimation(net.minecraft.entity.Entity entity, int animationType) {
        this.entityId = entity.getEntityId();
        this.animationType = animationType;
    }

    public int getEntityID() {
        return entityId;
    }

    public int getAnimationType() {
        return animationType;
    }
}
