package net.minecraftforge.event;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * 1.12.2 registry event. Fired by the loader after construction and before
 * {@code preInit}, which is when Forge mods expect to add blocks and items.
 */
public class RegistryEvent<T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> extends Event {
    private final ResourceLocation name;

    public RegistryEvent(ResourceLocation name) {
        this.name = name;
    }

    public ResourceLocation getName() {
        return name;
    }

    public static class Register<T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> extends RegistryEvent<T> {
        private final IForgeRegistry<T> registry;

        public Register(IForgeRegistry<T> registry) {
            super(registry.getRegistryName());
            this.registry = registry;
        }

        public IForgeRegistry<T> getRegistry() {
            return registry;
        }
    }
}
