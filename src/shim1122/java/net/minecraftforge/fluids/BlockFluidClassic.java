package net.minecraftforge.fluids;

import net.minecraft.block.material.Material;

public class BlockFluidClassic extends BlockFluidBase {
    public static final java.util.Map<net.minecraft.block.Block, Boolean> defaultDisplacements =
            new java.util.HashMap<>();
    public static final net.minecraft.block.properties.PropertyInteger LEVEL =
            net.minecraft.block.properties.PropertyInteger.create("level", 0, 15);

    public BlockFluidClassic(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Override
    public BlockFluidBase setQuantaPerBlock(int quanta) {
        return this;
    }
}
