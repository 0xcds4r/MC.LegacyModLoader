package net.minecraftforge.client.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DrawBlockHighlightEvent extends Event {
    private final Object context;
    private final RayTraceResult target;
    private final int subID;
    private final float partialTicks;
    private final EntityPlayer player;

    public DrawBlockHighlightEvent(Object context, EntityPlayer player, RayTraceResult target,
                                   int subID, float partialTicks) {
        this.context = context;
        this.player = player;
        this.target = target;
        this.subID = subID;
        this.partialTicks = partialTicks;
    }

    public Object getContext() {
        return context;
    }

    public RayTraceResult getTarget() {
        return target;
    }

    public int getSubID() {
        return subID;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
