package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockDoublePlant extends BlockBush {
    public BlockDoublePlant() {
        super(Material.VINE);
    }

    public enum EnumPlantType {
        SUNFLOWER,
        SYRINGA,
        GRASS,
        FERN,
        ROSE,
        PAEONIA
    }
}
