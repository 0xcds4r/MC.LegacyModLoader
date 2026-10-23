package net.minecraft.nbt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map-backed NBT shim. It preserves the 1.12.2 API surface used by simple mods
 * (configuration, tile entity data) without depending on modern components.
 */
public class NBTTagCompound extends NBTBase {
    private final Map<String, Object> values = new LinkedHashMap<>();

    @Override
    public byte getId() {
        return 10;
    }

    public void setInteger(String key, int value) {
        values.put(key, value);
    }

    public int getInteger(String key) {
        Object value = values.get(key);
        return value instanceof Number number ? number.intValue() : 0;
    }

    public void setFloat(String key, float value) {
        values.put(key, value);
    }

    public float getFloat(String key) {
        Object value = values.get(key);
        return value instanceof Number number ? number.floatValue() : 0.0F;
    }

    public void setDouble(String key, double value) {
        values.put(key, value);
    }

    public double getDouble(String key) {
        Object value = values.get(key);
        return value instanceof Number number ? number.doubleValue() : 0.0D;
    }

    public void setLong(String key, long value) {
        values.put(key, value);
    }

    public long getLong(String key) {
        Object value = values.get(key);
        return value instanceof Number number ? number.longValue() : 0L;
    }

    public void setBoolean(String key, boolean value) {
        values.put(key, value);
    }

    public boolean getBoolean(String key) {
        Object value = values.get(key);
        return value instanceof Boolean bool && bool;
    }

    public void setString(String key, String value) {
        values.put(key, value);
    }

    public String getString(String key) {
        Object value = values.get(key);
        return value instanceof String string ? string : "";
    }

    public void setTag(String key, NBTBase value) {
        values.put(key, value);
    }

    public NBTTagCompound getCompoundTag(String key) {
        Object value = values.get(key);
        return value instanceof NBTTagCompound compound ? compound : null;
    }

    public boolean hasKey(String key) {
        return values.containsKey(key);
    }

    public boolean hasKey(String key, int type) {
        return values.containsKey(key);
    }

    public void removeTag(String key) {
        values.remove(key);
    }

    public Set<String> getKeySet() {
        return Collections.unmodifiableSet(values.keySet());
    }

    public NBTTagCompound copy() {
        NBTTagCompound copy = new NBTTagCompound();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            Object value = entry.getValue();
            copy.values.put(entry.getKey(), value instanceof NBTTagCompound compound ? compound.copy() : value);
        }
        return copy;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
