package net.minecraftforge.event.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityEvent;

public class ItemExpireEvent extends EntityEvent {
    private int extraLife;

    public ItemExpireEvent(EntityItem item, int extraLife) {
        super(item);
        this.extraLife = extraLife;
    }

    public EntityItem getEntityItem() {
        return (EntityItem) entity;
    }

    public int getExtraLife() {
        return extraLife;
    }

    public void setExtraLife(int extraLife) {
        this.extraLife = extraLife;
    }
}
