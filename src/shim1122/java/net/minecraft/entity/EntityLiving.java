package net.minecraft.entity;

import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityLiving extends EntityLivingBase {
    public PathNavigate navigator;

    public EntityLiving(World world) {
        super(world);
    }

    public PathNavigate getNavigator() {
        return navigator;
    }
}
