package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketInput extends Packet<INetHandler> {
    private final float strafeSpeed;
    private final float forwardSpeed;
    private final boolean jumping;
    private final boolean sneaking;

    public CPacketInput(float strafeSpeed, float forwardSpeed, boolean jumping, boolean sneaking) {
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    public float getStrafeSpeed() {
        return strafeSpeed;
    }

    public float getForwardSpeed() {
        return forwardSpeed;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isSneaking() {
        return sneaking;
    }
}
