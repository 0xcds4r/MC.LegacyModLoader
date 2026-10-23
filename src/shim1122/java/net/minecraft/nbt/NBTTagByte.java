package net.minecraft.nbt;

public class NBTTagByte extends NBTBase {
    private final byte value;

    public NBTTagByte(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override
    public byte getId() {
        return 1;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagByte(value);
    }

    @Override
    public String toString() {
        return Byte.toString(value);
    }
}
