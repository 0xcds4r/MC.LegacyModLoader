package net.minecraft.network.datasync;

public class DataSerializer<T> {
    private final String name;

    public DataSerializer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
