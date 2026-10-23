package net.minecraft.network.play.client;

import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class CPacketClickWindow extends Packet<INetHandler> {
    private final int windowId;
    private final int slotId;
    private final int mouseButton;
    private final ClickType clickType;
    private final ItemStack clickedItem;
    private final short actionNumber;

    public CPacketClickWindow(int windowId, int slotId, int mouseButton, ClickType clickType,
                              ItemStack clickedItem, short actionNumber) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.mouseButton = mouseButton;
        this.clickType = clickType;
        this.clickedItem = clickedItem;
        this.actionNumber = actionNumber;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    public short getActionNumber() {
        return actionNumber;
    }
}
