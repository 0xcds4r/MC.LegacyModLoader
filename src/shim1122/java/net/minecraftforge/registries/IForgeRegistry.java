package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 1.12.2 registry facade. Only the members used by normal content mods are
 * present; everything else is deliberately absent so missing usage fails with a
 * precise error instead of silently doing nothing.
 */
public interface IForgeRegistry<V extends IForgeRegistryEntry<V>> extends Iterable<V> {
    void register(V value);

    void registerAll(V... values);

    boolean containsKey(ResourceLocation key);

    V getValue(ResourceLocation key);

    Set<ResourceLocation> getKeys();

    Collection<V> getValues();

    default Collection<V> getValuesCollection() {
        return getValues();
    }

    Class<V> getRegistrySuperType();

    ResourceLocation getRegistryName();

    boolean isEmpty();

    @Override
    Iterator<V> iterator();
}
