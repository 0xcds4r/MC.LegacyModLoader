package net.minecraftforge.fml.common.gameevent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class TickEvent extends Event {
    public final Phase phase;
    public final Side side;

    public TickEvent(Phase phase, Side side) {
        this.phase = phase;
        this.side = side;
    }

    public enum Phase {
        START,
        END
    }

    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase) {
            super(phase, Side.CLIENT);
        }
    }

    public static class ServerTickEvent extends TickEvent {
        public ServerTickEvent(Phase phase) {
            super(phase, Side.SERVER);
        }
    }

    public static class PlayerTickEvent extends TickEvent {
        public final EntityPlayer player;

        public PlayerTickEvent(Phase phase, EntityPlayer player) {
            super(phase, player.getEntityWorld().isRemote ? Side.CLIENT : Side.SERVER);
            this.player = player;
        }
    }

    public static class WorldTickEvent extends TickEvent {
        public final net.minecraft.world.World world;

        public WorldTickEvent(Phase phase, net.minecraft.world.World world) {
            super(phase, world.isRemote ? Side.CLIENT : Side.SERVER);
            this.world = world;
        }
    }

    public static class RenderTickEvent extends TickEvent {
        public final float renderTickTime;

        public RenderTickEvent(Phase phase, float renderTickTime) {
            super(phase, Side.CLIENT);
            this.renderTickTime = renderTickTime;
        }
    }
}
