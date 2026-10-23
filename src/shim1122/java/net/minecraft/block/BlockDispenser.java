package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.RegistryDefaulted;

public class BlockDispenser extends Block {
    public static final RegistryDefaulted<Item, IBehaviorDispenseItem> DISPENSE_BEHAVIOR_REGISTRY =
            new RegistryDefaulted<>(null);

    public BlockDispenser() {
        super(Material.ROCK);
    }
}
