package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;

public class CPacketUpdateSign extends Packet<INetHandler> {
    private final BlockPos pos;
    private final String[] lines;

    public CPacketUpdateSign(BlockPos pos, String[] lines) {
        this.pos = pos;
        this.lines = lines;
    }

    public BlockPos getPos() {
        return pos;
    }

    public String[] getLines() {
        return lines;
    }
}
