package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerTryUseItemOnBlock extends Packet<INetHandler> {
    private final BlockPos pos;
    private final EnumFacing facing;
    private final EnumHand hand;
    private final float facingX;
    private final float facingY;
    private final float facingZ;

    public CPacketPlayerTryUseItemOnBlock(BlockPos pos, EnumFacing facing, EnumHand hand,
                                          float x, float y, float z) {
        this.pos = pos;
        this.facing = facing;
        this.hand = hand;
        this.facingX = x;
        this.facingY = y;
        this.facingZ = z;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getDirection() {
        return facing;
    }

    public EnumHand getHand() {
        return hand;
    }
}
