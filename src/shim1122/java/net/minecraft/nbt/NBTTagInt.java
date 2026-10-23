package net.minecraft.nbt;

public class NBTTagInt extends NBTBase {
    private final int value;

    public NBTTagInt(int value) {
        this.value = value;
    }

    public int getInt() {
        return value;
    }

    @Override
    public byte getId() {
        return 3;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagInt(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
