package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;

public class CPacketTabComplete extends Packet<INetHandler> {
    private final String message;
    private final boolean hasTargetBlock;
    private final BlockPos targetBlock;

    public CPacketTabComplete(String message, BlockPos targetBlock, boolean hasTargetBlock) {
        this.message = message;
        this.targetBlock = targetBlock;
        this.hasTargetBlock = hasTargetBlock;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasTargetBlock() {
        return hasTargetBlock;
    }

    public BlockPos getTargetBlock() {
        return targetBlock;
    }
}
