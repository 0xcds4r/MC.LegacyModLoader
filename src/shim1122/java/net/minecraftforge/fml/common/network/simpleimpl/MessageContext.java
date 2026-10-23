package net.minecraftforge.fml.common.network.simpleimpl;

import net.minecraft.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageContext {
    private final Side side;

    public MessageContext(Side side) {
        this.side = side;
    }

    public Side side() {
        return side;
    }

    public NetHandlerPlayServer getServerHandler() {
        return new NetHandlerPlayServer();
    }

    public NetHandlerPlayClient getClientHandler() {
        return new NetHandlerPlayClient();
    }
}
