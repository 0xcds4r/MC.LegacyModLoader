package net.minecraft.item;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmor extends Item {
    public final ArmorMaterial material;
    public final int renderIndex;
    public final EntityEquipmentSlot armorType;
    public int damageReduceAmount;

    public ItemArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
        this.material = material;
        this.renderIndex = renderIndex;
        this.armorType = armorType;
        this.damageReduceAmount = material.getDamageReductionAmount(armorType);
        setMaxStackSize(1);
    }

    public int getArmorMaterialDamageReduction() {
        return material.getDamageReductionAmount(armorType);
    }

    public int getMaxDamage() {
        return material.getDurability(armorType);
    }

    @Override
    public dev.legacyloader.bridge.LegacyArmorSlot legacyArmorSlot() {
        return switch (armorType) {
            case HEAD -> dev.legacyloader.bridge.LegacyArmorSlot.HEAD;
            case CHEST -> dev.legacyloader.bridge.LegacyArmorSlot.CHEST;
            case LEGS -> dev.legacyloader.bridge.LegacyArmorSlot.LEGS;
            case FEET -> dev.legacyloader.bridge.LegacyArmorSlot.FEET;
            default -> null;
        };
    }

    @Override
    public String legacyArmorMaterial() {
        return material.getName();
    }

    /**
     * 1.12 uses an enum for vanilla materials and {@code EnumHelper} to create
     * modded ones. A class with the same public constants keeps both working
     * and preserves the mod's material name, which is needed to find the old
     * armor layer textures.
     */
    public static final class ArmorMaterial {
        public static final ArmorMaterial LEATHER = new ArmorMaterial("LEATHER", 5, new int[]{1, 3, 2, 1}, 15);
        public static final ArmorMaterial CHAIN = new ArmorMaterial("CHAINMAIL", 15, new int[]{2, 5, 4, 1}, 12);
        public static final ArmorMaterial IRON = new ArmorMaterial("IRON", 15, new int[]{2, 6, 5, 2}, 9);
        public static final ArmorMaterial GOLD = new ArmorMaterial("GOLD", 7, new int[]{1, 3, 2, 1}, 25);
        public static final ArmorMaterial DIAMOND = new ArmorMaterial("DIAMOND", 33, new int[]{3, 8, 6, 3}, 10);

        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;

        public ArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability) {
            this.name = name == null ? "iron" : name;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
        }

        public int getDurability(EntityEquipmentSlot slot) {
            int base = switch (slot) {
                case FEET -> 13;
                case LEGS -> 15;
                case CHEST -> 16;
                case HEAD -> 11;
                default -> 0;
            };
            return base * maxDamageFactor;
        }

        public int getDamageReductionAmount(EntityEquipmentSlot slot) {
            int index = switch (slot) {
                case FEET -> 0;
                case LEGS -> 1;
                case CHEST -> 2;
                case HEAD -> 3;
                default -> -1;
            };
            return index < 0 ? 0 : damageReductionAmountArray[index];
        }

        public int getEnchantability() {
            return enchantability;
        }

        public String getName() {
            return name;
        }

        public ItemStack getRepairItemStack() {
            return ItemStack.EMPTY;
        }
    }
}
