package dev.legacyloader.asset;

import dev.legacyloader.bridge.impl.FabricBridge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * Dedicated creative tab for legacy content. Without this, items exist in the
 * registry and are obtainable with /give but do not appear in the creative
 * inventory.
 */
public final class LegacyCreativeTab {
    private static final Identifier ID = Identifier.fromNamespaceAndPath("legacyloader", "legacy");

    private LegacyCreativeTab() {
    }

    public static void register(FabricBridge bridge) {
        if (bridge.itemsById().isEmpty()) {
            return;
        }
        Map<Identifier, ?> items = bridge.itemsById();
        Identifier firstId = items.keySet().iterator().next();
        Item first = BuiltInRegistries.ITEM.getValue(firstId);
        if (first == null) {
            return;
        }
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ID);
        CreativeModeTab tab = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.literal("Legacy Mods"))
                .icon(() -> new ItemStack(first))
                .displayItems((parameters, output) -> {
                    for (Identifier id : items.keySet()) {
                        Item item = BuiltInRegistries.ITEM.getValue(id);
                        if (item != null) {
                            output.accept(new ItemStack(item));
                        }
                    }
                })
                .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
        org.slf4j.LoggerFactory.getLogger("legacyloader/assets")
                .info("Registered legacy creative tab with {} items", items.size());
    }
}
