package net.minecraftforge.event.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ItemTossEvent extends PlayerEvent {
    private final EntityItem entityItem;

    public ItemTossEvent(EntityItem entityItem, EntityPlayer player) {
        super(player);
        this.entityItem = entityItem;
    }

    public EntityItem getEntityItem() {
        return entityItem;
    }
}
