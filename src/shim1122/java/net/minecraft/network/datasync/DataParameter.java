package net.minecraft.network.datasync;

public class DataParameter<T> {
    private final int id;
    private final DataSerializer<T> serializer;

    public DataParameter(int id, DataSerializer<T> serializer) {
        this.id = id;
        this.serializer = serializer;
    }

    public int getId() {
        return id;
    }

    public DataSerializer<T> getSerializer() {
        return serializer;
    }
}
