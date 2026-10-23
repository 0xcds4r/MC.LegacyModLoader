package net.minecraft.util.registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrySimple<K, V> {
    protected final Map<K, V> registryObjects = new LinkedHashMap<>();

    public void putObject(K key, V value) {
        registryObjects.put(key, value);
    }

    public V getObject(K key) {
        return registryObjects.get(key);
    }

    public boolean containsKey(K key) {
        return registryObjects.containsKey(key);
    }

    public Map<K, V> getRegistryObjects() {
        return registryObjects;
    }
}
