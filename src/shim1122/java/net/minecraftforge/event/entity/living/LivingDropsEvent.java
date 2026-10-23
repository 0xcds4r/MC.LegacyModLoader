package net.minecraftforge.event.entity.living;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;

import java.util.List;

public class LivingDropsEvent extends LivingEvent {
    private final DamageSource source;
    private final List<EntityItem> drops;
    private final int lootingLevel;
    private final boolean recentlyHit;

    public LivingDropsEvent(net.minecraft.entity.EntityLivingBase entity, DamageSource source,
                            List<EntityItem> drops, int lootingLevel, boolean recentlyHit) {
        super(entity);
        this.source = source;
        this.drops = drops;
        this.lootingLevel = lootingLevel;
        this.recentlyHit = recentlyHit;
    }

    public DamageSource getSource() {
        return source;
    }

    public List<EntityItem> getDrops() {
        return drops;
    }

    public int getLootingLevel() {
        return lootingLevel;
    }

    public boolean isRecentlyHit() {
        return recentlyHit;
    }
}
