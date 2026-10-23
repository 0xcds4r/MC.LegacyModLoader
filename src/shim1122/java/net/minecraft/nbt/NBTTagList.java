package net.minecraft.nbt;

/**
 * List-backed NBT shim used by capabilities and tile entity data.
 */
public class NBTTagList extends NBTBase {
    private final java.util.List<NBTBase> tags = new java.util.ArrayList<>();

    @Override
    public byte getId() {
        return 9;
    }

    public void appendTag(NBTBase tag) {
        if (tag != null) {
            tags.add(tag);
        }
    }

    public void set(int index, NBTBase tag) {
        tags.set(index, tag);
    }

    public NBTBase removeTag(int index) {
        return tags.remove(index);
    }

    public int tagCount() {
        return tags.size();
    }

    public boolean hasNoTags() {
        return tags.isEmpty();
    }

    public NBTTagCompound getCompoundTagAt(int index) {
        NBTBase tag = tags.get(index);
        return tag instanceof NBTTagCompound compound ? compound : new NBTTagCompound();
    }

    public int getIntAt(int index) {
        NBTBase tag = tags.get(index);
        return tag instanceof NBTTagInt integer ? integer.getInt() : 0;
    }

    public String getStringTagAt(int index) {
        NBTBase tag = tags.get(index);
        return tag instanceof NBTTagString string ? string.getString() : "";
    }

    @Override
    public NBTBase copy() {
        NBTTagList copy = new NBTTagList();
        for (NBTBase tag : tags) {
            copy.appendTag(tag.copy());
        }
        return copy;
    }

    @Override
    public String toString() {
        return tags.toString();
    }
}
