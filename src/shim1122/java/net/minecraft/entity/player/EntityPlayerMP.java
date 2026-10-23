package net.minecraft.entity.player;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class EntityPlayerMP extends EntityPlayer {
    public NetHandlerPlayServer connection;
    public MinecraftServer mcServer;

    public EntityPlayerMP(World world) {
        super(world);
    }

    public MinecraftServer getServer() {
        return mcServer;
    }

    public void sendMessage(net.minecraft.util.text.ITextComponent message) {
    }
}
