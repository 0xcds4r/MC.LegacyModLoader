package net.minecraft.network.datasync;

import java.util.HashMap;
import java.util.Map;

public class EntityDataManager {
    private final Map<DataParameter<?>, Object> values = new HashMap<>();
    private int nextId;

    public static <T> DataParameter<T> createKey(Class<? extends T> type, DataSerializer<T> serializer) {
        return new DataParameter<>(0, serializer);
    }

    public <T> void register(DataParameter<T> key, T value) {
        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(DataParameter<T> key) {
        return (T) values.get(key);
    }

    public <T> void set(DataParameter<T> key, T value) {
        values.put(key, value);
    }

    public boolean isDirty() {
        return false;
    }
}
