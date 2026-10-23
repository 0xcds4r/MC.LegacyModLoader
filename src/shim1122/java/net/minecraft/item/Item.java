package net.minecraft.item;

import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyItemView;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 1.12.2 item shim. Plain POJO by design; the modern item object is a separate
 * adapter in the loader.
 */
public class Item implements IForgeRegistryEntry<Item>, LegacyItemView {
    public static final net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Item> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespaced<>();
    protected int maxStackSize = 64;
    protected int maxDamage;
    protected boolean hasSubtypes;
    protected boolean alwaysEdible;
    public boolean canRepair = true;
    protected CreativeTabs creativeTab;
    protected String unlocalizedName;
    protected ResourceLocation registryName;

    public Item() {
    }

    public Item setMaxStackSize(int size) {
        this.maxStackSize = size;
        return this;
    }

    public Item setMaxDamage(int damage) {
        this.maxDamage = damage;
        this.maxStackSize = 1;
        return this;
    }

    public Item setNoRepair() {
        return this;
    }

    public void addPropertyOverride(net.minecraft.util.ResourceLocation key, IItemPropertyGetter getter) {
    }

    public Item setFull3D() {
        return this;
    }

    public Item setContainerItem(Item containerItem) {
        return this;
    }

    public Item setHasSubtypes(boolean hasSubtypes) {
        this.hasSubtypes = hasSubtypes;
        return this;
    }

    public Item setCreativeTab(CreativeTabs tab) {
        this.creativeTab = tab;
        return this;
    }

    public Item setUnlocalizedName(String name) {
        this.unlocalizedName = name;
        return this;
    }

    public Item setTranslationKey(String key) {
        return setUnlocalizedName(key);
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public boolean isDamageable() {
        return maxDamage > 0;
    }

    public boolean isRepairable() {
        return canRepair && isDamageable();
    }

    public boolean getHasSubtypes() {
        return hasSubtypes;
    }

    public CreativeTabs getCreativeTab() {
        return creativeTab;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public String getTranslationKey() {
        return unlocalizedName;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return unlocalizedName;
    }

    @Override
    public String legacyTranslationKey(int metadata) {
        String key = getUnlocalizedName(new ItemStack(this, 1, metadata));
        if (key == null || key.isBlank()) {
            return "item.unnamed";
        }
        return key.startsWith("item.") || key.startsWith("tile.") ? key : "item." + key;
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return getUnlocalizedName(stack);
    }

    @Override
    public Item setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    public static Item getItemFromBlock(net.minecraft.block.Block block) {
        return net.minecraft.item.ItemBlock.itemsByBlock.get(block);
    }

    public static Item getByNameOrId(String id) {
        try {
            return REGISTRY.getObject(new ResourceLocation(id));
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static int getIdFromItem(Item item) {
        return REGISTRY.getIDForObject(item);
    }

    public static Item getItemById(int id) {
        return REGISTRY.getObjectById(id);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public Class<Item> getRegistrySuperType() {
        return Item.class;
    }

    // --- LegacyItemView --------------------------------------------------

    @Override
    public String legacyTranslationKey() {
        String key = unlocalizedName == null ? "unnamed" : unlocalizedName;
        return key.startsWith("item.") ? key : "item." + key;
    }

    @Override
    public String legacyRegistryPath() {
        if (registryName != null) {
            return registryName.getPath();
        }
        String key = legacyTranslationKey();
        int dot = key.lastIndexOf('.');
        return dot >= 0 ? key.substring(dot + 1) : key;
    }

    @Override
    public int legacyMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public int legacyMaxDamage() {
        return maxDamage;
    }

    @Override
    public boolean legacyHasSubtypes() {
        return hasSubtypes;
    }

    @Override
    public boolean legacyIsFood() {
        return false;
    }

    @Override
    public LegacyBlockView legacyBlock() {
        return null;
    }

    public enum ToolMaterial {
        WOOD(0, 59, 2.0F, 0.0F, 15),
        STONE(1, 131, 4.0F, 1.0F, 5),
        IRON(2, 250, 6.0F, 2.0F, 14),
        DIAMOND(3, 1561, 8.0F, 3.0F, 10),
        GOLD(0, 32, 12.0F, 0.0F, 22);

        private final int harvestLevel;
        private final int maxUses;
        private final float efficiency;
        private final float damageVsEntity;
        private final int enchantability;

        ToolMaterial(int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability) {
            this.harvestLevel = harvestLevel;
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.damageVsEntity = damageVsEntity;
            this.enchantability = enchantability;
        }

        public int getHarvestLevel() {
            return harvestLevel;
        }

        public int getMaxUses() {
            return maxUses;
        }

        public float getEfficiency() {
            return efficiency;
        }

        public float getAttackDamage() {
            return damageVsEntity;
        }

        public int getEnchantability() {
            return enchantability;
        }

        public ItemStack getRepairItemStack() {
            return ItemStack.EMPTY;
        }

        public ToolMaterial setRepairItem(ItemStack stack) {
            return this;
        }
    }
}
