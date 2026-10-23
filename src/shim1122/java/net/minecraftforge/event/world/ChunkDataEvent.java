package net.minecraftforge.event.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChunkDataEvent extends Event {
    private final Chunk chunk;

    public ChunkDataEvent(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public static class Save extends ChunkDataEvent {
        private final NBTTagCompound data;

        public Save(Chunk chunk, NBTTagCompound data) {
            super(chunk);
            this.data = data;
        }

        public NBTTagCompound getData() {
            return data;
        }
    }

    public static class Load extends ChunkDataEvent {
        private final NBTTagCompound data;

        public Load(Chunk chunk, NBTTagCompound data) {
            super(chunk);
            this.data = data;
        }

        public NBTTagCompound getData() {
            return data;
        }
    }
}
