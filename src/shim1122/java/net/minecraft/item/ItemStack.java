package net.minecraft.item;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 1.12.2 item stack shim. NBT is stored as a plain compound map; the bridge
 * will eventually map it onto modern data components.
 */
public final class ItemStack {
    public static final ItemStack EMPTY = new ItemStack((Item) null);

    private Item item;
    private int count;
    private int metadata;
    private NBTTagCompound tag;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        this(item, count, 0);
    }

    public ItemStack(Item item, int count, int metadata) {
        this.item = item;
        this.count = item == null ? 0 : count;
        this.metadata = metadata;
    }

    public ItemStack(net.minecraft.block.Block block) {
        this(Item.getItemFromBlock(block));
    }

    public ItemStack(net.minecraft.block.Block block, int count) {
        this(Item.getItemFromBlock(block), count);
    }

    public ItemStack(net.minecraft.block.Block block, int count, int metadata) {
        this(Item.getItemFromBlock(block), count, metadata);
    }

    public boolean isEmpty() {
        return item == null || count <= 0;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMetadata() {
        return metadata;
    }

    public int getItemDamage() {
        return metadata;
    }

    public void setItemDamage(int metadata) {
        this.metadata = metadata;
    }

    public int getMaxStackSize() {
        return item == null ? 0 : item.getMaxStackSize();
    }

    public ItemStack copy() {
        ItemStack copy = new ItemStack(item, count, metadata);
        if (tag != null) {
            copy.tag = tag.copy();
        }
        return copy;
    }

    public ItemStack splitStack(int amount) {
        int taken = Math.min(amount, count);
        ItemStack result = new ItemStack(item, taken, metadata);
        count -= taken;
        return result;
    }

    public String getUnlocalizedName() {
        return item == null ? "air" : item.getUnlocalizedName(this);
    }

    public String getTranslationKey() {
        return getUnlocalizedName();
    }

    public String getDisplayName() {
        return item == null ? "Air" : item.getItemStackDisplayName(this);
    }

    public boolean hasTagCompound() {
        return tag != null;
    }

    public NBTTagCompound getTagCompound() {
        return tag;
    }

    public NBTTagCompound getOrCreateSubCompound(String key) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        NBTTagCompound sub = tag.getCompoundTag(key);
        if (sub == null) {
            sub = new NBTTagCompound();
            tag.setTag(key, sub);
        }
        return sub;
    }

    public void setTagCompound(NBTTagCompound tag) {
        this.tag = tag;
    }

    public void setTagInfo(String key, net.minecraft.nbt.NBTBase value) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        tag.setTag(key, value);
    }

    public void setStackDisplayName(String name) {
        // display names are a modern data component; recorded by the bridge later
    }

    public NBTTagCompound serializeNBT() {        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Count", count);
        compound.setInteger("Damage", metadata);
        if (tag != null) {
            compound.setTag("tag", tag.copy());
        }
        return compound;
    }

    public void deserializeNBT(NBTTagCompound compound) {        this.count = compound.getInteger("Count");
        this.metadata = compound.getInteger("Damage");
        this.tag = compound.getCompoundTag("tag");
    }

    public net.minecraft.nbt.NBTTagList getEnchantmentTagList() {
        return new net.minecraft.nbt.NBTTagList();
    }
}
