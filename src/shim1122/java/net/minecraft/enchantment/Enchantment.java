package net.minecraft.enchantment;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Enchantment extends IForgeRegistryEntry.Impl<Enchantment> {
    public static final net.minecraft.util.registry.RegistryNamespaced<net.minecraft.util.ResourceLocation, Enchantment> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespaced<>();
    private final Rarity rarity;
    private final Type type;
    private final EntityEquipmentSlot[] applicableSlots;
    private String name = "";

    public Enchantment(Rarity rarity, Type type, EntityEquipmentSlot[] applicableSlots) {
        this.rarity = rarity;
        this.type = type;
        this.applicableSlots = applicableSlots;
    }

    public Enchantment setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return 1;
    }

    public int getMinLevel() {
        return 1;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Type getType() {
        return type;
    }

    public boolean canApplyAtEnchantingTable(net.minecraft.item.ItemStack stack) {
        return true;
    }

    public static int getEnchantmentId(Enchantment enchantment) {
        return 0;
    }

    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        VERY_RARE
    }

    public enum Type {
        ALL,
        ARMOR,
        ARMOR_FEET,
        ARMOR_LEGS,
        ARMOR_CHEST,
        ARMOR_HEAD,
        WEAPON,
        DIGGER,
        FISHING_ROD,
        BREAKABLE,
        BOW
    }
}
