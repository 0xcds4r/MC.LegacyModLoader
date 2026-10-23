package net.minecraftforge.registries;

import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyModContext;
import dev.legacyloader.bridge.LegacySoundView;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Routes {@code RegistryEvent.Register} calls into the loader bridge. A single
 * raw implementation serves every registry: the value type decides whether it
 * becomes a block, an item or an explicitly unsupported entry.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class BridgeRegistry implements IForgeRegistry {
    private final String registryName;
    private final Set<ResourceLocation> keys = new LinkedHashSet<>();

    public BridgeRegistry(String registryName) {
        this.registryName = registryName;
    }

    @Override
    public void register(IForgeRegistryEntry value) {
        if (value == null) {
            return;
        }
        LegacyModContext context = BridgeHolder.require().currentContext();
        ResourceLocation id = value.getRegistryName();
        if (id == null) {
            id = new ResourceLocation(context.modId(), fallbackPath(value));
            value.setRegistryName(id);
        } else if ("minecraft".equals(id.getNamespace())
                && !"minecraft".equalsIgnoreCase(context.modId())) {
            id = new ResourceLocation(context.modId(), id.getPath());
            value.setRegistryName(id);
        }
        keys.add(id);

        if (value instanceof LegacyBlockView block) {
            BridgeHolder.require().registerBlock(block, id.getNamespace(), id.getPath());
        } else if (value instanceof Item item) {
            BridgeHolder.require().registerItem(item, id.getNamespace(), id.getPath());
        } else if (value instanceof LegacySoundView sound) {
            BridgeHolder.require().registerSound(sound, id.getNamespace(), id.getPath());
        } else {
            BridgeHolder.require().reportUnsupported("registry " + registryName,
                    value.getClass().getName() + " (" + id + ")");
        }
    }

    @Override
    public void registerAll(IForgeRegistryEntry... values) {
        for (IForgeRegistryEntry value : values) {
            register(value);
        }
    }

    private static String fallbackPath(Object value) {
        if (value instanceof Block block) {
            return block.legacyRegistryPath();
        }
        if (value instanceof Item item) {
            return item.legacyRegistryPath();
        }
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return keys.contains(key);
    }

    @Override
    public IForgeRegistryEntry getValue(ResourceLocation key) {
        return null;
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return Collections.unmodifiableSet(keys);
    }

    @Override
    public Collection<IForgeRegistryEntry> getValues() {
        return Collections.emptyList();
    }

    @Override
    public Class getRegistrySuperType() {
        return IForgeRegistryEntry.class;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(registryName);
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public Iterator iterator() {
        return Collections.emptyIterator();
    }
}
