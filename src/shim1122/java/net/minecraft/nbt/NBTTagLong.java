package net.minecraft.nbt;

public class NBTTagLong extends NBTBase {
    private final long value;

    public NBTTagLong(long value) {
        this.value = value;
    }

    public long getLong() {
        return value;
    }

    @Override
    public byte getId() {
        return 4;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagLong(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
