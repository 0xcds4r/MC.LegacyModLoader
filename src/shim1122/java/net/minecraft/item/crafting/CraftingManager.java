package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager {
    public static final net.minecraft.util.registry.RegistryNamespaced<net.minecraft.util.ResourceLocation, IRecipe> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespaced<>();
    private static final CraftingManager INSTANCE = new CraftingManager();
    private final List<IRecipe> recipes = new ArrayList<>();

    public static CraftingManager getInstance() {
        return INSTANCE;
    }

    public List<IRecipe> getRecipeList() {
        return recipes;
    }

    public void addRecipe(IRecipe recipe) {
        recipes.add(recipe);
    }

    public ItemStack findMatchingRecipe(InventoryCrafting craftMatrix, World world) {
        return ItemStack.EMPTY;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World world) {
        return new ItemStack[craftMatrix.getSizeInventory()];
    }
}
