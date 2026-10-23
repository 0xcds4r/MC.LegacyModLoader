package net.minecraft.item;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.block.Block;

/**
 * 1.12.2 block item shim. Declaring {@link #legacyBlock()} is what tells the
 * bridge to register a real modern {@code BlockItem} instead of a plain item.
 */
public class ItemBlock extends Item {
    public static final java.util.Map<Block, Item> itemsByBlock = new java.util.HashMap<>();
    private final Block block;

    public ItemBlock(Block block) {
        this.block = block;
        if (block != null) {
            itemsByBlock.put(block, this);
        }
        if (block != null && block.getRegistryName() != null) {
            setRegistryName(block.getRegistryName());
        }
        setUnlocalizedName(block == null ? "legacy.item_block" : block.getUnlocalizedName());
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public LegacyBlockView legacyBlock() {
        return block;
    }

    @Override
    public String legacyTranslationKey() {
        return block == null ? "tile.unnamed" : block.legacyTranslationKey();
    }

    @Override
    public String legacyTranslationKey(int metadata) {
        return legacyTranslationKey();
    }
}
