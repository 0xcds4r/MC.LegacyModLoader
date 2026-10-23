package net.minecraftforge.oredict;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreDictionary {
    public static final int WILDCARD_VALUE = Short.MAX_VALUE;
    private static final Map<String, List<ItemStack>> ORES = new HashMap<>();

    public static void registerOre(String name, ItemStack ore) {
        ORES.computeIfAbsent(name, ignored -> new ArrayList<>()).add(ore);
    }

    public static void registerOre(String name, net.minecraft.item.Item item) {
        registerOre(name, new ItemStack(item));
    }

    public static void registerOre(String name, net.minecraft.block.Block block) {
        registerOre(name, new ItemStack(block));
    }

    public static List<ItemStack> getOres(String name) {
        return ORES.getOrDefault(name, List.of());
    }

    public static net.minecraft.util.NonNullList<ItemStack> getOres(String name, boolean alwaysCreateEntry) {
        net.minecraft.util.NonNullList<ItemStack> result = new net.minecraft.util.NonNullList<>();
        result.addAll(ORES.getOrDefault(name, List.of()));
        return result;
    }

    public static boolean doesOreExist(String name) {
        return ORES.containsKey(name);
    }

    public static String[] getOreNames() {
        return ORES.keySet().toArray(String[]::new);
    }

    public static int[] getOreIDs(ItemStack stack) {
        return new int[0];
    }

    public static String getOreName(int id) {
        return "";
    }

    public static int getOreID(String name) {
        return ORES.containsKey(name) ? name.hashCode() : -1;
    }

    public static int getOreID(ItemStack stack) {
        return -1;
    }
}
