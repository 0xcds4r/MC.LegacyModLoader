package net.minecraft.entity.passive;

import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAnimal extends EntityCreature {
    public EntityAnimal(World world) {
        super(world);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public void setInLove(net.minecraft.entity.player.EntityPlayer player) {
    }

    public boolean isInLove() {
        return false;
    }
}
