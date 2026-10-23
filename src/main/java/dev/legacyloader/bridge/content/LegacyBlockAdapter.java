package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * The modern block object that stands in for a legacy block. Legacy behaviour
 * is attached through {@link LegacyBlockView}; subclassing a modern block with
 * an old shim would inherit an incompatible method surface and leak the modern
 * hierarchy into legacy code, so composition is used instead.
 */
public final class LegacyBlockAdapter extends Block {
    private final LegacyBlockView view;

    public LegacyBlockAdapter(BlockBehaviour.Properties properties, LegacyBlockView view) {
        super(properties);
        this.view = view;
    }

    public LegacyBlockView legacyView() {
        return view;
    }
}
