package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumHand;

public class CPacketPlayerTryUseItem extends Packet<INetHandler> {
    private final EnumHand hand;

    public CPacketPlayerTryUseItem(EnumHand hand) {
        this.hand = hand;
    }

    public EnumHand getHand() {
        return hand;
    }
}
