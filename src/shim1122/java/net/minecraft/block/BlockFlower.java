package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockFlower extends BlockBush {
    public BlockFlower(Material material) {
        super(material);
    }

    public BlockFlower() {
        this(Material.PLANTS);
    }

    public enum EnumFlowerType {
        DANDELION,
        POPPY,
        BLUE_ORCHID,
        ALLIUM,
        HOUSTONIA,
        RED_TULIP,
        ORANGE_TULIP,
        WHITE_TULIP,
        PINK_TULIP,
        OXEYE_DAISY
    }
}
