package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class LegacyBushAdapter extends BushBlock {
    private final LegacyBlockView view;

    public LegacyBushAdapter(BlockBehaviour.Properties properties, LegacyBlockView view) {
        super(properties);
        this.view = view;
    }

    public LegacyBlockView legacyView() {
        return view;
    }
}
