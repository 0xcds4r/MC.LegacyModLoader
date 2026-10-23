package net.minecraftforge.oredict;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class ShapelessOreRecipe implements IRecipe {
    private final ResourceLocation name;

    public ShapelessOreRecipe(ResourceLocation name, Object... recipe) {
        this.name = name;
    }

    public ShapelessOreRecipe(Object... recipe) {
        this.name = new ResourceLocation("legacy", "shapeless");
    }

    public ResourceLocation getRegistryName() {
        return name;
    }
}
