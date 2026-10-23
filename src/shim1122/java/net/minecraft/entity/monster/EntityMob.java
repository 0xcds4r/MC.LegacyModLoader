package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityMob extends EntityCreature {
    public EntityMob(World world) {
        super(world);
    }

    public boolean attackEntityAsMob(net.minecraft.entity.Entity entity) {
        return false;
    }

    @Override
    public void setAttackTarget(EntityLivingBase target) {
        super.setAttackTarget(target);
    }
}
