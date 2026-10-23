package net.minecraftforge.event.entity.player;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EntityItemPickupEvent extends PlayerEvent {
    private final EntityItem item;

    public EntityItemPickupEvent(EntityPlayer player, EntityItem item) {
        super(player);
        this.item = item;
    }

    public EntityItem getItem() {
        return item;
    }
}
