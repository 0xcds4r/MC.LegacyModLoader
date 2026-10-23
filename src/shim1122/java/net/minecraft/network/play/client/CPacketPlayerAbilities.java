package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketPlayerAbilities extends Packet<INetHandler> {
    private final boolean invulnerable;
    private final boolean flying;
    private final boolean allowFlying;
    private final boolean creativeMode;

    public CPacketPlayerAbilities(net.minecraft.entity.player.PlayerCapabilities capabilities) {
        this.invulnerable = capabilities.disableDamage;
        this.flying = capabilities.isFlying;
        this.allowFlying = capabilities.allowFlying;
        this.creativeMode = capabilities.isCreativeMode;
    }
}
