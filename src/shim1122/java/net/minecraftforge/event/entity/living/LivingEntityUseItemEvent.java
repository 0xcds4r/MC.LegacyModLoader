package net.minecraftforge.event.entity.living;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class LivingEntityUseItemEvent extends LivingEvent {
    private final ItemStack item;
    private int duration;

    public LivingEntityUseItemEvent(EntityLivingBase entity, ItemStack item, int duration) {
        super(entity);
        this.item = item;
        this.duration = duration;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static class Finish extends LivingEntityUseItemEvent {
        public Finish(EntityLivingBase entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    public static class Start extends LivingEntityUseItemEvent {
        public Start(EntityLivingBase entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    public static class Stop extends LivingEntityUseItemEvent {
        public Stop(EntityLivingBase entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }

    public static class Tick extends LivingEntityUseItemEvent {
        public Tick(EntityLivingBase entity, ItemStack item, int duration) {
            super(entity, item, duration);
        }
    }
}
