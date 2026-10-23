package net.minecraft.entity.monster;

import net.minecraft.world.World;

public class EntitySlime extends EntityMob {
    public EntitySlime(World world) {
        super(world);
    }

    public int getSlimeSize() {
        return 1;
    }
}
