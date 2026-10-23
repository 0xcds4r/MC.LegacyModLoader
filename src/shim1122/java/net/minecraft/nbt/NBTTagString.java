package net.minecraft.nbt;

public class NBTTagString extends NBTBase {
    private final String value;

    public NBTTagString(String value) {
        this.value = value == null ? "" : value;
    }

    public String getString() {
        return value;
    }

    @Override
    public byte getId() {
        return 8;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagString(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
