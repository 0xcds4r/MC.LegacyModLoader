package net.minecraft.util;

import net.minecraft.entity.Entity;

public class EntityDamageSource extends DamageSource {
    protected Entity damageSourceEntity;

    public EntityDamageSource(String damageType, Entity source) {
        super(damageType);
        this.damageSourceEntity = source;
    }

    public Entity getSourceOfDamage() {
        return damageSourceEntity;
    }

    public Entity getEntity() {
        return damageSourceEntity;
    }
}
