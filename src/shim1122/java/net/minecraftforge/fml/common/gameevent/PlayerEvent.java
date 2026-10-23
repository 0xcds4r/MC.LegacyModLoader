package net.minecraftforge.fml.common.gameevent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerEvent extends Event {
    public final EntityPlayer player;

    public PlayerEvent(EntityPlayer player) {
        this.player = player;
    }

    public static class ItemCraftedEvent extends PlayerEvent {
        public final ItemStack crafting;
        public final IInventory inventory;

        public ItemCraftedEvent(EntityPlayer player, ItemStack crafting, IInventory inventory) {
            super(player);
            this.crafting = crafting;
            this.inventory = inventory;
        }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(EntityPlayer player) {
            super(player);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(EntityPlayer player) {
            super(player);
        }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        public PlayerRespawnEvent(EntityPlayer player) {
            super(player);
        }
    }
}
