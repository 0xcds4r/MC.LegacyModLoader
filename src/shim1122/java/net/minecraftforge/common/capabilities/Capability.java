package net.minecraftforge.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;

public class Capability<T> {
    public interface IStorage<T> {
        NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side);

        void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt);
    }

    private final String name;
    private IStorage<T> storage;
    private Callable<? extends T> factory;

    public Capability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public IStorage<T> getStorage() {
        return storage;
    }

    public void setStorage(IStorage<T> storage) {
        this.storage = storage;
    }

    public T getDefaultInstance() {
        try {
            return factory == null ? null : factory.call();
        } catch (Exception e) {
            return null;
        }
    }

    public void setFactory(Callable<? extends T> factory) {
        this.factory = factory;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface CapabilityInject {
    }
}
