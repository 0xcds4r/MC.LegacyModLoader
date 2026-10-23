package net.minecraftforge.event.entity.player;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerPickupXpEvent extends PlayerEvent {
    private final EntityXPOrb orb;

    public PlayerPickupXpEvent(EntityPlayer player, EntityXPOrb orb) {
        super(player);
        this.orb = orb;
    }

    public EntityXPOrb getOrb() {
        return orb;
    }
}
