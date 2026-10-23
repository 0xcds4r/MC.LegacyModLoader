package net.minecraft.util.registry;

public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V> {
    private final K defaultKey;

    public RegistryNamespacedDefaultedByKey(K defaultKey) {
        this.defaultKey = defaultKey;
    }

    @Override
    public V getObject(K key) {
        V value = super.getObject(key);
        return value == null ? super.getObject(defaultKey) : value;
    }
}
