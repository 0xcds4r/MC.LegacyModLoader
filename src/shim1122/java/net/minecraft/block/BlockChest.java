package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockChest extends Block {
    public BlockChest(Type type) {
        super(Material.WOOD);
    }

    public enum Type {
        BASIC,
        TRAP
    }
}
