package net.minecraftforge.event.entity.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerWakeUpEvent extends PlayerEvent {
    private final boolean wakeImmediately;
    private final boolean updateWorld;

    public PlayerWakeUpEvent(EntityPlayer player, boolean wakeImmediately, boolean updateWorld) {
        super(player);
        this.wakeImmediately = wakeImmediately;
        this.updateWorld = updateWorld;
    }

    public boolean wakeImmediately() {
        return wakeImmediately;
    }

    public boolean shouldSetSpawn() {
        return updateWorld;
    }
}
