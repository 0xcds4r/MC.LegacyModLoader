package net.minecraftforge.fluids;

import net.minecraft.item.Item;

public class UniversalBucket extends Item {
    public UniversalBucket() {
        setMaxStackSize(1);
    }

    public UniversalBucket(Fluid fluid, int amount, boolean hasContainerItem) {
        this();
    }
}
