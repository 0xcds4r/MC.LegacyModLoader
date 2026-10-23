package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityTippedArrow extends EntityArrow {
    public EntityTippedArrow(World world) {
        super(world);
    }

    public EntityTippedArrow(World world, EntityLivingBase shooter) {
        super(world, shooter);
    }

    public void addEffect(PotionEffect effect) {
    }
}
