package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class LegacyStairAdapter extends StairBlock {
    private final LegacyBlockView view;

    public LegacyStairAdapter(BlockState baseState, BlockBehaviour.Properties properties, LegacyBlockView view) {
        super(baseState, properties);
        this.view = view;
    }

    public LegacyBlockView legacyView() {
        return view;
    }
}
