package net.minecraftforge.event.entity.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerEvent extends Event {
    public final EntityPlayer player;

    public PlayerEvent(EntityPlayer player) {
        this.player = player;
    }

    public static class Clone extends PlayerEvent {
        private final EntityPlayer original;
        private final boolean wasDeath;

        public Clone(EntityPlayer original, EntityPlayer player, boolean wasDeath) {
            super(player);
            this.original = original;
            this.wasDeath = wasDeath;
        }

        public EntityPlayer getOriginal() {
            return original;
        }

        public boolean isWasDeath() {
            return wasDeath;
        }
    }
}
