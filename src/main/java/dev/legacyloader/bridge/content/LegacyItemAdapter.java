package dev.legacyloader.bridge.content;

import dev.legacyloader.bridge.LegacyItemView;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Modern stand-in for a legacy item that has no block form.
 */
public final class LegacyItemAdapter extends Item {
    private final LegacyItemView view;

    public LegacyItemAdapter(Properties properties, LegacyItemView view) {
        super(properties);
        this.view = view;
    }

    public LegacyItemView legacyView() {
        return view;
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
