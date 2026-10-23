package net.minecraft.util.registry;

import java.util.ArrayList;
import java.util.List;

public class RegistryNamespaced<K, V> extends RegistrySimple<K, V> {
    private final List<V> values = new ArrayList<>();

    public void register(int id, K key, V value) {
        while (values.size() <= id) {
            values.add(null);
        }
        values.set(id, value);
        putObject(key, value);
    }

    public V getObjectById(int id) {
        return id >= 0 && id < values.size() ? values.get(id) : null;
    }

    public int getIDForObject(V value) {
        return values.indexOf(value);
    }

    public K getNameForObject(V value) {
        for (var entry : registryObjects.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    public java.util.Set<K> getKeys() {
        return registryObjects.keySet();
    }

    public java.util.Iterator<V> iterator() {
        return values.iterator();
    }
}
