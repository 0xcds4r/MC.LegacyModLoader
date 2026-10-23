package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockLeaves extends Block {
    public static final net.minecraft.block.properties.PropertyBool CHECK_DECAY =
            net.minecraft.block.properties.PropertyBool.create("check_decay");
    public static final net.minecraft.block.properties.PropertyBool DECAYABLE =
            net.minecraft.block.properties.PropertyBool.create("decayable");

    public BlockLeaves() {
        super(Material.LEAVES);
    }
}
