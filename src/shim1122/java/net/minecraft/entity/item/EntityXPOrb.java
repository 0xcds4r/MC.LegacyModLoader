package net.minecraft.entity.item;

import net.minecraft.world.World;

public class EntityXPOrb extends net.minecraft.entity.Entity {
    public int xpValue;

    public EntityXPOrb(World world) {
        super(world);
    }

    public EntityXPOrb(World world, double x, double y, double z, int xpValue) {
        super(world);
        setPosition(x, y, z);
        this.xpValue = xpValue;
    }

    public int getXpValue() {
        return xpValue;
    }
}
