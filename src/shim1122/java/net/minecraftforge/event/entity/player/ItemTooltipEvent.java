package net.minecraftforge.event.entity.player;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;

public class ItemTooltipEvent extends PlayerEvent {
    private final ItemStack itemStack;
    private final List<String> toolTip;
    private final ITooltipFlag flags;

    public ItemTooltipEvent(ItemStack itemStack, EntityPlayer player, List<String> toolTip, ITooltipFlag flags) {
        super(player);
        this.itemStack = itemStack;
        this.toolTip = toolTip;
        this.flags = flags;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public List<String> getToolTip() {
        return toolTip;
    }

    public ITooltipFlag getFlags() {
        return flags;
    }
}
