package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketClientSettings extends Packet<INetHandler> {
    private final String language;
    private final int viewDistance;
    private final int chatVisibility;
    private final boolean chatColors;

    public CPacketClientSettings(String language, int viewDistance, int chatVisibility, boolean chatColors) {
        this.language = language;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.chatColors = chatColors;
    }
}
