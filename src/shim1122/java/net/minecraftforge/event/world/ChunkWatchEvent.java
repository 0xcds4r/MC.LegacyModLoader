package net.minecraftforge.event.world;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChunkWatchEvent extends Event {
    private final ChunkPos pos;
    private final EntityPlayerMP player;

    public ChunkWatchEvent(ChunkPos pos, EntityPlayerMP player) {
        this.pos = pos;
        this.player = player;
    }

    public ChunkPos getChunk() {
        return pos;
    }

    public EntityPlayerMP getPlayer() {
        return player;
    }

    public static class Watch extends ChunkWatchEvent {
        public Watch(ChunkPos pos, EntityPlayerMP player) {
            super(pos, player);
        }
    }

    public static class UnWatch extends ChunkWatchEvent {
        public UnWatch(ChunkPos pos, EntityPlayerMP player) {
            super(pos, player);
        }
    }
}
