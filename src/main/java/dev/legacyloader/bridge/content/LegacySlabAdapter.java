package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class LegacySlabAdapter extends SlabBlock {
    private final LegacyBlockView view;

    public LegacySlabAdapter(BlockBehaviour.Properties properties, LegacyBlockView view) {
        super(properties);
        this.view = view;
    }

    public LegacyBlockView legacyView() {
        return view;
    }
}
