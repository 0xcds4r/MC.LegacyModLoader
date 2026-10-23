package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

/**
 * Root interface for everything registerable in Forge 1.12.2. Keeping the exact
 * name and generic shape matters: old bytecode refers to the erased
 * {@code (Lnet/minecraftforge/registries/IForgeRegistryEntry;)...} descriptor.
 */
public interface IForgeRegistryEntry<V extends IForgeRegistryEntry<V>> {
    V setRegistryName(ResourceLocation name);

    ResourceLocation getRegistryName();

    Class<V> getRegistrySuperType();

    /**
     * String overloads live on the interface on purpose. 1.12 bytecode calls
     * them with the erased return type {@code IForgeRegistryEntry}; declaring
     * a covariant override in Block/Item would change the descriptor and break
     * linking.
     */
    default V setRegistryName(String name) {
        return setRegistryName(new ResourceLocation(name));
    }

    default V setRegistryName(String namespace, String path) {
        return setRegistryName(new ResourceLocation(namespace, path));
    }

    /**
     * Convenience base class used by many 1.12.2 mods.
     */
    class Impl<V extends IForgeRegistryEntry<V>> implements IForgeRegistryEntry<V> {
        protected ResourceLocation registryName;

        @Override
        public V setRegistryName(ResourceLocation name) {
            this.registryName = name;
            @SuppressWarnings("unchecked")
            V self = (V) this;
            return self;
        }

        @Override
        public ResourceLocation getRegistryName() {
            return registryName;
        }

        @Override
        public Class<V> getRegistrySuperType() {
            return null;
        }
    }
}
