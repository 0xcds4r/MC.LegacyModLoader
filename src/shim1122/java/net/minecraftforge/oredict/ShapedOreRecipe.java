package net.minecraftforge.oredict;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class ShapedOreRecipe implements IRecipe {
    private final ResourceLocation name;

    public ShapedOreRecipe(ResourceLocation name, Object... recipe) {
        this.name = name;
    }

    public ShapedOreRecipe(Object... recipe) {
        this.name = new ResourceLocation("legacy", "shaped");
    }

    public ResourceLocation getRegistryName() {
        return name;
    }
}
