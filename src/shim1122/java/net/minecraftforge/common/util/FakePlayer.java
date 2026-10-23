package net.minecraftforge.common.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;

public class FakePlayer extends net.minecraft.entity.player.EntityPlayerMP {
    public FakePlayer(WorldServer world, GameProfile profile) {
        super(world);
    }
}
