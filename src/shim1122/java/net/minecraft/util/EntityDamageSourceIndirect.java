package net.minecraft.util;

import net.minecraft.entity.Entity;

public class EntityDamageSourceIndirect extends EntityDamageSource {
    private final Entity indirectEntity;

    public EntityDamageSourceIndirect(String damageType, Entity source, Entity indirectEntity) {
        super(damageType, source);
        this.indirectEntity = indirectEntity;
    }

    public Entity getImmediateSource() {
        return damageSourceEntity;
    }

    public Entity getTrueSource() {
        return indirectEntity;
    }
}
