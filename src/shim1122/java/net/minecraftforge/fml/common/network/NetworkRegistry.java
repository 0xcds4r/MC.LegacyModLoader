package net.minecraftforge.fml.common.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkRegistry {
    public static final NetworkRegistry INSTANCE = new NetworkRegistry();

    private NetworkRegistry() {
    }

    public SimpleNetworkWrapper newSimpleChannel(String name) {
        return new SimpleNetworkWrapper(name);
    }

    public void registerGuiHandler(Object mod, IGuiHandler handler) {
        // GUI opening is not bridged yet; accepting the registration is enough
        // for mods to finish init without throwing.
    }

    public static class TargetPoint {
        public final int dimension;
        public final double x;
        public final double y;
        public final double z;
        public final double range;

        public TargetPoint(int dimension, double x, double y, double z, double range) {
            this.dimension = dimension;
            this.x = x;
            this.y = y;
            this.z = z;
            this.range = range;
        }
    }
}
