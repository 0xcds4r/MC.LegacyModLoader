package net.minecraftforge.event.entity.living;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class LivingDeathEvent extends LivingEvent {
    private final DamageSource source;

    public LivingDeathEvent(EntityLivingBase entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }
}
