package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerDigging extends Packet<INetHandler> {
    private final Action action;
    private final BlockPos position;
    private final EnumFacing facing;

    public CPacketPlayerDigging(Action action, BlockPos position, EnumFacing facing) {
        this.action = action;
        this.position = position;
        this.facing = facing;
    }

    public Action getAction() {
        return action;
    }

    public BlockPos getPosition() {
        return position;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_HELD_ITEMS
    }
}
