package net.minecraftforge.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class EnumHelper {
    private EnumHelper() {
    }

    public static Item.ToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses,
                                                    float efficiency, float damage, int enchantability) {
        if (maxUses > 1000) {
            return Item.ToolMaterial.DIAMOND;
        }
        if (maxUses > 150) {
            return Item.ToolMaterial.IRON;
        }
        if (maxUses > 60) {
            return Item.ToolMaterial.STONE;
        }
        return Item.ToolMaterial.WOOD;
    }

    public static ItemArmor.ArmorMaterial addArmorMaterial(String name, String textureName,
                                                           int durability, int[] reductionAmounts,
                                                           int enchantability) {
        return new ItemArmor.ArmorMaterial(name, durability, reductionAmounts, enchantability);
    }

    public static ItemArmor.ArmorMaterial addArmorMaterial(String name, String textureName,
                                                           int durability, int[] reductionAmounts,
                                                           int enchantability,
                                                           net.minecraft.util.SoundEvent equipSound,
                                                           float toughness) {
        return new ItemArmor.ArmorMaterial(name, durability, reductionAmounts, enchantability);
    }

    public static <T extends Enum<T>> T addEnum(Class<T> enumType, String name, Object[] params) {
        T[] values = enumType.getEnumConstants();
        return values.length > 0 ? values[0] : null;
    }
}
