package net.minecraft.dispenser;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense implements IBehaviorDispenseItem {
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        return stack;
    }

    protected abstract EntityThrowable getProjectileEntity(World world, IPosition position, ItemStack stack);
}
