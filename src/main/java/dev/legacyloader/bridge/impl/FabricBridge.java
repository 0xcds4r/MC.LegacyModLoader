package dev.legacyloader.bridge.impl;

import dev.legacyloader.api.LegacyProfile;
import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyBridge;
import dev.legacyloader.bridge.LegacyItemView;
import dev.legacyloader.bridge.LegacyModContext;
import dev.legacyloader.bridge.content.LegacyBlockAdapter;
import dev.legacyloader.bridge.content.LegacyContentRegistry;
import dev.legacyloader.bridge.content.LegacyItemAdapter;
import dev.legacyloader.classloader.PlatformGateway;
import dev.legacyloader.runtime.LegacyRuntime;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Fabric implementation of the legacy bridge. Everything in this class runs in
 * the loader's classloader and is the only place where legacy-facing data turns
 * into modern registry entries.
 */
public final class FabricBridge implements LegacyBridge {
    private static final Logger LOG = LoggerFactory.getLogger("legacyloader");

    private final LegacyRuntime runtime;
    private final LegacyProfile profile;
    private final PlatformGateway gateway;
    private final LegacyContentRegistry content = new LegacyContentRegistry();
    private final ThreadLocal<Deque<LegacyModContext>> contextStack = ThreadLocal.withInitial(ArrayDeque::new);
    private final List<Object> eventListeners = new CopyOnWriteArrayList<>();
    private final Map<Identifier, LegacyBlockView> blocksById = new LinkedHashMap<>();
    private final Map<Identifier, LegacyItemView> itemsById = new LinkedHashMap<>();
    private final Map<Identifier, dev.legacyloader.bridge.LegacySoundView> soundsById = new LinkedHashMap<>();
    private final Map<String, ArmorMaterial> customArmorMaterials = new java.util.HashMap<>();

    public FabricBridge(LegacyRuntime runtime, LegacyProfile profile, PlatformGateway gateway) {
        this.runtime = runtime;
        this.profile = profile;
        this.gateway = gateway;
    }

    public void pushContext(LegacyModContext context) {
        contextStack.get().push(context);
    }

    public void popContext() {
        Deque<LegacyModContext> stack = contextStack.get();
        if (!stack.isEmpty()) {
            stack.pop();
        }
        if (stack.isEmpty()) {
            contextStack.remove();
        }
    }

    @Override
    public LegacyModContext currentContext() {
        Deque<LegacyModContext> stack = contextStack.get();
        if (stack.isEmpty()) {
            throw new IllegalStateException("Legacy API used outside a legacy mod phase");
        }
        return stack.peek();
    }

    @Override
    public void registerBlock(LegacyBlockView block, String namespace, String path) {
        LegacyModContext context = currentContext();
        Identifier id = identifier(namespace, path, context);
        try {
            if (BuiltInRegistries.BLOCK.containsKey(id)) {
                reportDegraded("block registration", "duplicate block id " + id + " from " + context.modId());
                return;
            }
            BlockBehaviour.Properties properties = blockProperties(block, id);
            Block modern = switch (block.legacyKind()) {
                case SLAB -> new dev.legacyloader.bridge.content.LegacySlabAdapter(properties, block);
                case STAIRS -> new dev.legacyloader.bridge.content.LegacyStairAdapter(
                        net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), properties, block);
                case BUSH, CROPS -> new dev.legacyloader.bridge.content.LegacyBushAdapter(properties, block);
                default -> new LegacyBlockAdapter(properties, block);
            };
            Registry.register(BuiltInRegistries.BLOCK, id, modern);
            content.linkBlock(block, modern);
            blocksById.put(id, block);
            LOG.info("[{}] registered block {}", context.modId(), id);
        } catch (Throwable t) {
            reportUnsupported("block registration", id + " failed: " + t);
            LOG.error("[{}] failed to register block {}", context.modId(), id, t);
        }
    }

    @Override
    public void registerItem(LegacyItemView item, String namespace, String path) {
        LegacyModContext context = currentContext();
        Identifier id = identifier(namespace, path, context);
        try {
            if (BuiltInRegistries.ITEM.containsKey(id)) {
                reportDegraded("item registration", "duplicate item id " + id + " from " + context.modId());
                return;
            }
            Item.Properties properties = itemProperties(item, id, context);

            LegacyBlockView blockView = item.legacyBlock();
            Item modern;
            if (blockView != null) {
                Object modernBlock = content.modernBlock(blockView);
                if (!(modernBlock instanceof Block block)) {
                    reportDegraded("block item registration",
                            id + " cannot be registered because its block is missing");
                    return;
                }
                properties.useBlockDescriptionPrefix();
                modern = new dev.legacyloader.bridge.content.LegacyBlockItem(block, properties, item);
            } else {
                properties.useItemDescriptionPrefix();
                modern = new LegacyItemAdapter(properties, item);
            }
            Registry.register(BuiltInRegistries.ITEM, id, modern);
            content.linkItem(item, modern);
            itemsById.put(id, item);
            LOG.info("[{}] registered item {}", context.modId(), id);
        } catch (Throwable t) {
            reportUnsupported("item registration", id + " failed: " + t);
            LOG.error("[{}] failed to register item {}", context.modId(), id, t);
        }
    }

    private BlockBehaviour.Properties blockProperties(LegacyBlockView view, Identifier id) {
        float hardness = view.legacyHardness();
        float resistance = Math.max(0.0F, view.legacyResistance());
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .overrideDescription(descriptionKey(view.legacyTranslationKey()))
                .strength(hardness < 0.0F ? -1.0F : hardness, resistance)
                .friction(Math.max(0.0F, Math.min(1.0F, view.legacyFriction())))
                .lightLevel(state -> Math.max(0, Math.min(15, view.legacyLightLevel())));
        if (!view.legacyOpaqueCube()) {
            properties.noOcclusion();
        }
        return properties;
    }

    private static String descriptionKey(String key) {
        if (key == null || key.isBlank()) {
            return "legacy.unnamed";
        }
        return key.endsWith(".name") ? key : key + ".name";
    }

    private Item.Properties itemProperties(LegacyItemView item, Identifier id, LegacyModContext context) {
        Item.Properties properties = new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, id))
                .overrideDescription(descriptionKey(item.legacyTranslationKey()));
        if (item.legacyMaxStackSize() > 0) {
            properties.stacksTo(Math.min(item.legacyMaxStackSize(), 99));
        }
        if (item.legacyMaxDamage() > 0) {
            properties.durability(item.legacyMaxDamage());
        }
        if (item.legacyArmorSlot() != null) {
            properties.humanoidArmor(armorMaterial(item.legacyArmorMaterial(), context),
                    armorType(item.legacyArmorSlot()));
            LOG.info("[{}] mapped armor {} slot={} material={}", context.modId(), id,
                    item.legacyArmorSlot(), item.legacyArmorMaterial());
        } else {
            ToolMaterial material = toolMaterial(item.legacyToolMaterial());
            float damage = item.legacyAttackDamage();
            float speed = item.legacyAttackSpeed();
            switch (item.legacyToolKind()) {
                case SWORD -> properties.sword(material, damage, speed);
                case PICKAXE -> properties.pickaxe(material, damage, speed);
                case AXE -> properties.axe(material, damage, speed);
                case SHOVEL -> properties.shovel(material, damage, speed);
                case HOE -> properties.hoe(material, damage, speed);
                default -> {
                }
            }
        }
        if (item.legacyIsFood()) {
            properties.food(new net.minecraft.world.food.FoodProperties(
                    item.legacyFoodNutrition(), item.legacyFoodSaturation(), item.legacyAlwaysEdible()));
        }
        return properties;
    }

    private static ToolMaterial toolMaterial(String name) {
        return switch (name == null ? "IRON" : name.toUpperCase(Locale.ROOT)) {
            case "WOOD" -> ToolMaterial.WOOD;
            case "STONE" -> ToolMaterial.STONE;
            case "GOLD" -> ToolMaterial.GOLD;
            case "DIAMOND" -> ToolMaterial.DIAMOND;
            case "NETHERITE" -> ToolMaterial.NETHERITE;
            default -> ToolMaterial.IRON;
        };
    }

    private ArmorMaterial armorMaterial(String name, LegacyModContext context) {
        String normalized = name == null ? "IRON" : name.toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "LEATHER" -> net.minecraft.world.item.equipment.ArmorMaterials.LEATHER;
            case "CHAIN", "CHAINMAIL" -> net.minecraft.world.item.equipment.ArmorMaterials.CHAINMAIL;
            case "GOLD" -> net.minecraft.world.item.equipment.ArmorMaterials.GOLD;
            case "DIAMOND" -> net.minecraft.world.item.equipment.ArmorMaterials.DIAMOND;
            case "NETHERITE" -> net.minecraft.world.item.equipment.ArmorMaterials.NETHERITE;
            case "IRON" -> net.minecraft.world.item.equipment.ArmorMaterials.IRON;
            default -> customArmorMaterials.computeIfAbsent(normalized,
                    key -> createCustomArmorMaterial(key, context));
        };
    }

    private ArmorMaterial createCustomArmorMaterial(String name, LegacyModContext context) {
        int[] defense = switch (name) {
            case "VOIDROBE" -> new int[]{4, 7, 9, 4};
            case "VOID" -> new int[]{3, 6, 8, 3};
            case "FORTRESS" -> new int[]{3, 6, 7, 3};
            case "SPECIAL" -> new int[]{1, 2, 3, 1};
            case "CULTIST_ROBE" -> new int[]{2, 4, 5, 2};
            default -> new int[]{2, 5, 6, 2};
        };
        int durability = switch (name) {
            case "VOID", "CULTIST_ROBE", "CULTIST_PLATE" -> 15;
            case "FORTRESS" -> 40;
            default -> 25;
        };
        Map<ArmorType, Integer> defenseMap = Map.of(
                ArmorType.BOOTS, defense[0],
                ArmorType.LEGGINGS, defense[1],
                ArmorType.CHESTPLATE, defense[2],
                ArmorType.HELMET, defense[3]
        );
        @SuppressWarnings("unchecked")
        ResourceKey<Registry<EquipmentAsset>> root =
                (ResourceKey<Registry<EquipmentAsset>>) (ResourceKey<?>) EquipmentAssets.ROOT_ID;
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(root,
                Identifier.fromNamespaceAndPath(context.modId(),
                        "legacy_" + name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_")));
        Holder<SoundEvent> sound = net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_IRON;
        return new ArmorMaterial(durability, defenseMap, 9, sound, 0.0F, 0.0F,
                ItemTags.REPAIRS_IRON_ARMOR, asset);
    }

    private static net.minecraft.world.item.equipment.ArmorType armorType(dev.legacyloader.bridge.LegacyArmorSlot slot) {
        return switch (slot) {
            case HEAD -> net.minecraft.world.item.equipment.ArmorType.HELMET;
            case CHEST -> net.minecraft.world.item.equipment.ArmorType.CHESTPLATE;
            case LEGS -> net.minecraft.world.item.equipment.ArmorType.LEGGINGS;
            case FEET -> net.minecraft.world.item.equipment.ArmorType.BOOTS;
        };
    }

    private Identifier identifier(String namespace, String path, LegacyModContext context) {        String cleanNamespace = namespace == null || namespace.isBlank()
                ? context.modId() : namespace.toLowerCase(Locale.ROOT);
        String cleanPath = path == null || path.isBlank()
                ? "unnamed" : path.toLowerCase(Locale.ROOT);
        if (!Identifier.isValidPath(cleanPath)) {
            cleanPath = cleanPath.replaceAll("[^a-z0-9_./-]", "_");
        }
        return Identifier.fromNamespaceAndPath(cleanNamespace, cleanPath);
    }

    @Override
    public void registerEventListener(Object listener) {
        eventListeners.add(listener);
    }

    @Override
    public void registerSound(dev.legacyloader.bridge.LegacySoundView sound, String namespace, String path) {
        LegacyModContext context = currentContext();
        Identifier id = identifier(namespace, path, context);
        try {
            if (BuiltInRegistries.SOUND_EVENT.containsKey(id)) {
                reportDegraded("sound registration", "duplicate sound id " + id + " from " + context.modId());
                return;
            }
            String soundNamespace = "minecraft".equals(sound.legacySoundNamespace())
                    && !"minecraft".equalsIgnoreCase(context.modId())
                    ? context.modId() : sound.legacySoundNamespace();
            Identifier soundName = Identifier.fromNamespaceAndPath(soundNamespace, sound.legacySoundPath());
            net.minecraft.sounds.SoundEvent modern =
                    net.minecraft.sounds.SoundEvent.createVariableRangeEvent(soundName);
            Registry.register(BuiltInRegistries.SOUND_EVENT, id, modern);
            soundsById.put(id, sound);
            LOG.info("[{}] registered sound {}", context.modId(), id);
        } catch (Throwable t) {
            reportUnsupported("sound registration", id + " failed: " + t);
        }
    }

    @Override
    public void unregisterEventListener(Object listener) {
        eventListeners.remove(listener);
    }

    @Override
    public boolean isModLoaded(String modId) {
        return runtime.isModLoaded(modId);
    }

    @Override
    public void reportUnsupported(String feature, String detail) {
        runtime.reportUnsupported(feature, detail);
    }

    @Override
    public void reportDegraded(String feature, String detail) {
        runtime.reportUnsupported("[degraded] " + feature, detail);
    }

    @Override
    public void log(String level, String message) {
        switch (level == null ? "info" : level.toLowerCase(Locale.ROOT)) {
            case "error" -> LOG.error(message);
            case "warn" -> LOG.warn(message);
            case "debug" -> LOG.debug(message);
            default -> LOG.info(message);
        }
    }

    public LegacyContentRegistry content() {
        return content;
    }

    public Map<Identifier, LegacyBlockView> blocksById() {
        return blocksById;
    }

    public Map<Identifier, LegacyItemView> itemsById() {
        return itemsById;
    }

    public Map<Identifier, dev.legacyloader.bridge.LegacySoundView> soundsById() {
        return soundsById;
    }

    public List<Object> eventListeners() {
        return eventListeners;
    }

    public LegacyProfile profile() {
        return profile;
    }

    public PlatformGateway gateway() {
        return gateway;
    }
}
