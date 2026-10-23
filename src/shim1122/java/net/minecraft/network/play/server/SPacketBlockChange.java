package net.minecraft.network.play.server;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;

public class SPacketBlockChange extends Packet<INetHandler> {
    private BlockPos blockPosition;
    private IBlockState blockState;

    public SPacketBlockChange() {
    }

    public SPacketBlockChange(net.minecraft.world.World world, BlockPos pos) {
        this.blockPosition = pos;
        this.blockState = world.getBlockState(pos);
    }

    public BlockPos getBlockPosition() {
        return blockPosition;
    }

    public IBlockState getBlockState() {
        return blockState;
    }
}
