package net.minecraft.nbt;

public abstract class NBTBase {
    public abstract byte getId();

    public abstract NBTBase copy();

    public boolean hasNoTags() {
        return false;
    }

    @Override
    public abstract String toString();
}
