package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyBlockView;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Bidirectional mapping between legacy shim objects and the modern objects
 * registered in their place. Kept out of the shim layer so shims stay unaware
 * of modern registry types.
 */
public final class LegacyContentRegistry {
    private final Map<LegacyBlockView, Object> blocks = new IdentityHashMap<>();
    private final Map<LegacyItemViewKey, Object> items = new IdentityHashMap<>();

    public void linkBlock(LegacyBlockView legacy, Object modern) {
        blocks.put(legacy, modern);
    }

    public Object modernBlock(LegacyBlockView legacy) {
        return blocks.get(legacy);
    }

    public void linkItem(Object legacy, Object modern) {
        items.put(new LegacyItemViewKey(legacy), modern);
    }

    public Object modernItem(Object legacy) {
        return items.get(new LegacyItemViewKey(legacy));
    }

    public int blockCount() {
        return blocks.size();
    }

    public int itemCount() {
        return items.size();
    }

    /** Identity wrapper that keeps equals/hashCode out of shim code. */
    private record LegacyItemViewKey(Object value) {
        @Override
        public boolean equals(Object other) {
            return other instanceof LegacyItemViewKey key && key.value == value;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(value);
        }
    }
}
