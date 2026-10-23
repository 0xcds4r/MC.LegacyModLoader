package net.minecraftforge.event.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class NoteBlockEvent extends Event {
    private final World world;
    private final BlockPos pos;
    private final int note;

    public NoteBlockEvent(World world, BlockPos pos, int note) {
        this.world = world;
        this.pos = pos;
        this.note = note;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getNote() {
        return note;
    }

    public static class Play extends NoteBlockEvent {
        public Play(World world, BlockPos pos, int note) {
            super(world, pos, note);
        }
    }

    public static class Change extends NoteBlockEvent {
        public Change(World world, BlockPos pos, int note) {
            super(world, pos, note);
        }
    }
}
