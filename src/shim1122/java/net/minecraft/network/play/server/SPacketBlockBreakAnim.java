package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;

public class SPacketBlockBreakAnim extends Packet<INetHandler> {
    private int breakerId;
    private BlockPos position;
    private int progress;

    public SPacketBlockBreakAnim() {
    }

    public SPacketBlockBreakAnim(int breakerId, BlockPos position, int progress) {
        this.breakerId = breakerId;
        this.position = position;
        this.progress = progress;
    }

    public int getBreakerId() {
        return breakerId;
    }

    public BlockPos getPosition() {
        return position;
    }

    public int getProgress() {
        return progress;
    }
}
