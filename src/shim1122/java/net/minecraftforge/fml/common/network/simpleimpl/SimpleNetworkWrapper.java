package net.minecraftforge.fml.common.network.simpleimpl;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Channel shim. Messages are registered and dropped for now; real packet
 * translation to modern payloads is a later phase.
 */
public class SimpleNetworkWrapper {
    private final String channelName;

    public SimpleNetworkWrapper(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler,
            Class<REQ> requestMessageType,
            int discriminator,
            Side side) {
        // registration only
    }

    public void sendToServer(IMessage message) {
    }

    public void sendTo(IMessage message, EntityPlayerMP player) {
    }

    public void sendToAll(IMessage message) {
    }

    public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
    }

    public void sendToDimension(IMessage message, int dimensionId) {
    }
}
