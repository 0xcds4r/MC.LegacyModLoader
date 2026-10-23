package net.minecraft.dispenser;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IPosition {
    double getX();

    double getY();

    double getZ();

    net.minecraft.util.math.BlockPos getBlockPos();
}
