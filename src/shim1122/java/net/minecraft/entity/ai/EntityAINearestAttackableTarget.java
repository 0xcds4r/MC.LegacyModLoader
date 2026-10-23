package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;
import java.util.Comparator;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget {
    protected final Class<T> targetClass;
    private final int targetChance;
    private final Sorter theNearestAttackableTargetSorter;

    public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
        this(creature, classTarget, checkSight, false);
    }

    public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget,
                                           boolean checkSight, boolean onlyNearby) {
        this(creature, classTarget, 10, checkSight, onlyNearby, null);
    }

    public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, int chance,
                                           boolean checkSight, boolean onlyNearby,
                                           @Nullable java.util.function.Predicate<T> targetSelector) {
        super(creature, checkSight, onlyNearby);
        this.targetClass = classTarget;
        this.targetChance = chance;
        this.theNearestAttackableTargetSorter = new Sorter(creature);
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }

    public static class Sorter implements Comparator<net.minecraft.entity.Entity> {
        private final net.minecraft.entity.Entity entity;

        public Sorter(net.minecraft.entity.Entity entity) {
            this.entity = entity;
        }

        @Override
        public int compare(net.minecraft.entity.Entity first, net.minecraft.entity.Entity second) {
            double d0 = entity.getDistanceSq(first.getPosition());
            double d1 = entity.getDistanceSq(second.getPosition());
            return Double.compare(d0, d1);
        }
    }
}
