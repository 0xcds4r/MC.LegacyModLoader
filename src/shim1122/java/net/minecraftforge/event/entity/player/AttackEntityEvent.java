package net.minecraftforge.event.entity.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class AttackEntityEvent extends PlayerEvent {
    private final Entity target;
    private final net.minecraft.util.math.RayTraceResult result;

    public AttackEntityEvent(EntityPlayer player, Entity target, net.minecraft.util.math.RayTraceResult result) {
        super(player);
        this.target = target;
        this.result = result;
    }

    public Entity getTarget() {
        return target;
    }

    public net.minecraft.util.math.RayTraceResult getResult() {
        return result;
    }
}
