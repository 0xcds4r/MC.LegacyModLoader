package net.minecraft.item.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private final List<ItemStack> stacks = new ArrayList<>();

    protected Ingredient() {
    }

    public static Ingredient fromStacks(ItemStack... stacks) {
        Ingredient ingredient = new Ingredient();
        for (ItemStack stack : stacks) {
            ingredient.stacks.add(stack);
        }
        return ingredient;
    }

    public static Ingredient fromItems(net.minecraft.item.Item... items) {
        Ingredient ingredient = new Ingredient();
        for (net.minecraft.item.Item item : items) {
            ingredient.stacks.add(new ItemStack(item));
        }
        return ingredient;
    }

    public ItemStack[] getMatchingStacks() {
        return stacks.toArray(ItemStack[]::new);
    }

    public boolean apply(ItemStack stack) {
        for (ItemStack candidate : stacks) {
            if (candidate.getItem() == stack.getItem()) {
                return true;
            }
        }
        return false;
    }

    public boolean test(ItemStack stack) {
        return apply(stack);
    }
}
