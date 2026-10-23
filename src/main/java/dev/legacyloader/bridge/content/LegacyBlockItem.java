package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyItemView;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public final class LegacyBlockItem extends BlockItem {
    private final LegacyItemView view;

    public LegacyBlockItem(Block block, Properties properties, LegacyItemView view) {
        super(block, properties);
        this.view = view;
    }

    @Override
    public Component getName(ItemStack stack) {
        String key = view.legacyTranslationKey(stack.getDamageValue());
        return key == null || key.isBlank() ? super.getName(stack)
                : Component.translatable(descriptionKey(key));
    }

    private static String descriptionKey(String key) {
        return key.endsWith(".name") ? key : key + ".name";
    }
}
