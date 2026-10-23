package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItem extends Entity {
    private ItemStack item = ItemStack.EMPTY;
    public int age;
    public int lifespan = 6000;

    public EntityItem(World world) {
        super(world);
    }

    public EntityItem(World world, double x, double y, double z, ItemStack stack) {
        super(world);
        setPosition(x, y, z);
        this.item = stack;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
