package net.minecraftforge.fml.common.registry;

import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyModContext;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Forge 1.12.2 registry facade. Registrations are translated immediately into
 * modern registry entries through the bridge.
 */
public final class GameRegistry {
    private GameRegistry() {
    }

    public static <K extends IForgeRegistryEntry<K>> K register(K thing) {
        return register(thing, thing.getRegistryName());
    }

    public static <K extends IForgeRegistryEntry<K>> K register(K thing, ResourceLocation name) {
        if (name != null) {
            thing.setRegistryName(name);
        }
        LegacyModContext context = BridgeHolder.require().currentContext();
        ResourceLocation id = thing.getRegistryName();
        if (id == null) {
            id = new ResourceLocation(context.modId(), fallbackPath(thing));
            thing.setRegistryName(id);
        } else if ("minecraft".equals(id.getNamespace())
                && !"minecraft".equalsIgnoreCase(context.modId())) {
            id = new ResourceLocation(context.modId(), id.getPath());
            thing.setRegistryName(id);
        }

        if (thing instanceof LegacyBlockView block) {
            BridgeHolder.require().registerBlock(block, id.getNamespace(), id.getPath());
        } else if (thing instanceof Item item) {
            BridgeHolder.require().registerItem(item, id.getNamespace(), id.getPath());
        } else {
            BridgeHolder.require().reportUnsupported("registry",
                    "unsupported 1.12 registry entry " + thing.getClass().getName());
        }
        return thing;
    }

    private static String fallbackPath(Object thing) {
        if (thing instanceof Block block) {
            return block.legacyRegistryPath();
        }
        if (thing instanceof Item item) {
            return item.legacyRegistryPath();
        }
        return thing.getClass().getSimpleName().toLowerCase();
    }

    public static void addRecipe(IRecipe recipe) {
        BridgeHolder.require().reportUnsupported("crafting recipe",
                "1.12 IRecipe registration is not implemented; recipe JSON support is a later phase");
    }

    public static void addShapedRecipe(ResourceLocation name, String pattern, Object... ingredients) {
        BridgeHolder.require().reportUnsupported("shaped recipe", name.toString());
    }

    public static void addShapelessRecipe(ResourceLocation name, Object... ingredients) {
        BridgeHolder.require().reportUnsupported("shapeless recipe", name.toString());
    }

    public static void addSmelting(ItemStack input, ItemStack output, float experience) {
        BridgeHolder.require().reportUnsupported("smelting recipe",
                String.valueOf(output == null ? "null" : output.getUnlocalizedName()));
    }

    public static void addSmelting(net.minecraft.block.Block input, ItemStack output, float experience) {
        addSmelting(new ItemStack(input), output, experience);
    }

    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
        BridgeHolder.require().reportUnsupported("tile entity",
                tileEntityClass.getName() + " (" + id + ")");
    }

    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, ResourceLocation id) {
        registerTileEntity(tileEntityClass, id.toString());
    }

    public static void registerFuelHandler(net.minecraftforge.fml.common.IFuelHandler handler) {
        BridgeHolder.require().reportDegraded("fuel handler",
                handler.getClass().getName() + " (fuel values are not bridged yet)");
    }

    public static void registerWorldGenerator(net.minecraftforge.fml.common.IWorldGenerator generator, int weight) {
        BridgeHolder.require().reportUnsupported("world generator",
                generator.getClass().getName() + " (world generation is not bridged yet)");
    }
}
