package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class SPacketUpdateTileEntity extends Packet<INetHandler> {
    private BlockPos pos;
    private int tileEntityType;
    private NBTTagCompound nbt;

    public SPacketUpdateTileEntity() {
    }

    public SPacketUpdateTileEntity(BlockPos pos, int tileEntityType, NBTTagCompound nbt) {
        this.pos = pos;
        this.tileEntityType = tileEntityType;
        this.nbt = nbt;
    }

    public BlockPos getPos() {
        return pos;
    }

    public NBTTagCompound getNbtCompound() {
        return nbt;
    }

    public int getTileEntityType() {
        return tileEntityType;
    }
}
