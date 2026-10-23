package net.minecraftforge.event;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttachCapabilitiesEvent<T> extends Event {
    private final T object;
    private final Map<ResourceLocation, ICapabilityProvider> capabilities = new LinkedHashMap<>();

    public AttachCapabilitiesEvent(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void addCapability(ResourceLocation key, ICapabilityProvider capability) {
        capabilities.put(key, capability);
    }

    public Map<ResourceLocation, ICapabilityProvider> getCapabilities() {
        return capabilities;
    }
}
