package net.minecraftforge.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;

public final class CraftingHelper {
    private CraftingHelper() {
    }

    public static ItemStack getItemStack(JsonObject json) {
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNbt) {
        return ItemStack.EMPTY;
    }

    public static net.minecraft.item.crafting.Ingredient getIngredient(JsonObject json) {
        return net.minecraft.item.crafting.Ingredient.fromStacks();
    }

    public static net.minecraft.item.crafting.Ingredient getIngredient(Object json) {
        return net.minecraft.item.crafting.Ingredient.fromStacks();
    }
}
