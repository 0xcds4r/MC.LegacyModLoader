package net.minecraftforge.common.capabilities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class CapabilityManager {
    public static final CapabilityManager INSTANCE = new CapabilityManager();

    private final Map<Class<?>, Capability<?>> capabilities = new LinkedHashMap<>();

    private CapabilityManager() {
    }

    public <T> void register(Class<T> type, Capability.IStorage<T> storage,
                             Callable<? extends T> factory) {
        Capability<T> capability = new Capability<>(type.getName());
        capability.setStorage(storage);
        capability.setFactory(factory);
        capabilities.put(type, capability);
    }

    @SuppressWarnings("unchecked")
    public <T> Capability<T> get(Class<T> type) {
        return (Capability<T>) capabilities.get(type);
    }
}
