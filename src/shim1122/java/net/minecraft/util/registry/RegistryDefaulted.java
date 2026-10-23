package net.minecraft.util.registry;

public class RegistryDefaulted<K, V> extends RegistrySimple<K, V> {
    private final V defaultObject;

    public RegistryDefaulted(V defaultObject) {
        this.defaultObject = defaultObject;
    }

    @Override
    public V getObject(K key) {
        V value = super.getObject(key);
        return value == null ? defaultObject : value;
    }
}
