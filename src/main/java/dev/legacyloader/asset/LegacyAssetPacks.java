package dev.legacyloader.asset;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import dev.legacyloader.LegacyLoaderConfig;
import dev.legacyloader.bridge.LegacyItemView;
import dev.legacyloader.bridge.impl.FabricBridge;
import dev.legacyloader.runtime.LegacyModContainer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.ModEnvironment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Mounts a legacy jar's {@code assets/} as a real resource pack and converts
 * the 1.12 asset formats that modern Minecraft no longer reads.
 *
 * <p>The original jar is never modified: assets are copied into a generated
 * pack directory under {@code config/legacyloader/resourcepacks/}.
 */
public final class LegacyAssetPacks {
    private static final Logger LOG = LoggerFactory.getLogger("legacyloader/assets");
    private static final String GENERATOR_VERSION = "assets-v1";
    private static final GsonBuilder GSON = new GsonBuilder().setPrettyPrinting();

    private LegacyAssetPacks() {
    }

    public static Path prepare(LegacyLoaderConfig config, List<LegacyModContainer> containers) {
        Path root = config.gameDirectory().resolve("config").resolve("legacyloader").resolve("resourcepacks");
        for (LegacyModContainer container : containers) {
            try {
                Path packDir = root.resolve(container.modId());
                extractAssets(container.sourceJar(), packDir);
                lowercaseTree(packDir.resolve("assets"));
                migrateTextures(packDir);
                convertLanguages(packDir);
                writePackMeta(packDir, container.modName());
                if (!registerPack(packDir, container)) {
                    LOG.warn("Could not register legacy resource pack for {}", container.modId());
                }
            } catch (Throwable t) {
                LOG.warn("Cannot prepare assets for {}: {}", container.modId(), t.toString());
            }
        }
        return root;
    }

    /**
     * Modern item rendering uses {@code assets/<ns>/items/<name>.json} model
     * definitions. 1.12 mods only ship {@code models/item}. Generate the
     * definitions (and a fallback model when only a texture exists).
     */
    public static void writeItemModels(Path root, FabricBridge bridge) {
        writeArmorAssets(root, bridge);
        writeBlockAssets(root, bridge);
        for (Map.Entry<Identifier, LegacyItemView> entry : bridge.itemsById().entrySet()) {
            Identifier id = entry.getKey();
            LegacyItemView item = entry.getValue();
            try {
                Path assets = findAssets(root, id.getNamespace());
                if (assets == null) {
                    continue;
                }
                Path namespaceRoot = assets.resolve(id.getNamespace());
                Path itemModel = namespaceRoot.resolve("models").resolve("item").resolve(id.getPath() + ".json");
                Path blockModel = namespaceRoot.resolve("models").resolve("block").resolve(id.getPath() + ".json");
                Path blockState = namespaceRoot.resolve("blockstates").resolve(id.getPath() + ".json");

                String model;
                if (Files.isRegularFile(itemModel)) {
                    model = id.getNamespace() + ":item/" + id.getPath();
                } else if (item.legacyBlock() != null && Files.isRegularFile(blockModel)) {
                    model = id.getNamespace() + ":block/" + id.getPath();
                } else if (item.legacyBlock() != null && Files.isRegularFile(blockState)) {
                    model = id.getNamespace() + ":block/" + id.getPath();
                } else {
                    model = synthesizeItemModel(namespaceRoot, id);
                    if (model == null) {
                        model = writePlaceholderItemModel(namespaceRoot, id);
                    }
                }

                Path definitions = namespaceRoot.resolve("items");
                Files.createDirectories(definitions);
                JsonObject definition = new JsonObject();
                JsonObject modelObject = new JsonObject();
                modelObject.addProperty("type", "minecraft:model");
                modelObject.addProperty("model", model);
                definition.add("model", modelObject);
                Files.writeString(definitions.resolve(id.getPath() + ".json"),
                        GSON.create().toJson(definition), StandardCharsets.UTF_8);
            } catch (Throwable t) {
                LOG.debug("Cannot generate item model for {}: {}", id, t.toString());
            }
        }
    }

    private static void writeArmorAssets(Path root, FabricBridge bridge) {
        Map<String, String> materialTextures = Map.of(
                "THAUMIUM", "thaumium",
                "VOID", "void",
                "VOIDROBE", "void_robe_armor",
                "SPECIAL", "robes",
                "FORTRESS", "fortress_armor",
                "CULTIST_PLATE", "cultist_plate_armor",
                "CULTIST_ROBE", "cultist_robe_armor",
                "CULTIST_LEADER", "cultist_leader_armor",
                "GOGGLES", "goggles"
        );
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (Map.Entry<Identifier, LegacyItemView> entry : bridge.itemsById().entrySet()) {
            Identifier id = entry.getKey();
            LegacyItemView item = entry.getValue();
            if (item.legacyArmorSlot() == null) {
                continue;
            }
            String material = item.legacyArmorMaterial().toUpperCase(java.util.Locale.ROOT)
                    .replaceAll("[^A-Z0-9_]", "_");
            String key = id.getNamespace() + ":" + material;
            if (!seen.add(key)) {
                continue;
            }
            try {
                Path assets = findAssets(root, id.getNamespace());
                if (assets == null) {
                    continue;
                }
                Path namespaceRoot = assets.resolve(id.getNamespace());
                Path armorTextures = namespaceRoot.resolve("textures").resolve("entity").resolve("armor");
                String base = materialTextures.getOrDefault(material, material.toLowerCase(java.util.Locale.ROOT));
                Path layer1 = findArmorTexture(armorTextures, base, 1);
                Path layer2 = findArmorTexture(armorTextures, base, 2);
                String assetPath = "legacy_" + material.toLowerCase(java.util.Locale.ROOT);
                Path humanoid = namespaceRoot.resolve("textures").resolve("entity")
                        .resolve("equipment").resolve("humanoid");
                Path leggings = namespaceRoot.resolve("textures").resolve("entity")
                        .resolve("equipment").resolve("humanoid_leggings");
                Files.createDirectories(humanoid);
                Files.createDirectories(leggings);
                if (layer1 != null) {
                    Files.copy(layer1, humanoid.resolve(assetPath + "_layer_1.png"),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                if (layer2 != null) {
                    Files.copy(layer2, leggings.resolve(assetPath + "_layer_2.png"),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                JsonObject layers = new JsonObject();
                JsonArray humanoidLayers = new JsonArray();
                JsonObject humanoidLayer = new JsonObject();
                humanoidLayer.addProperty("texture", layer1 == null
                        ? "minecraft:iron" : id.getNamespace() + ":" + assetPath + "_layer_1");
                humanoidLayers.add(humanoidLayer);
                layers.add("humanoid", humanoidLayers);

                JsonArray leggingsLayers = new JsonArray();
                JsonObject leggingsLayer = new JsonObject();
                leggingsLayer.addProperty("texture", layer2 == null
                        ? "minecraft:iron" : id.getNamespace() + ":" + assetPath + "_layer_2");
                leggingsLayers.add(leggingsLayer);
                layers.add("humanoid_leggings", leggingsLayers);

                JsonObject equipment = new JsonObject();
                equipment.add("layers", layers);
                Path equipmentDir = namespaceRoot.resolve("equipment");
                Files.createDirectories(equipmentDir);
                Files.writeString(equipmentDir.resolve(assetPath + ".json"),
                        GSON.create().toJson(equipment), StandardCharsets.UTF_8);
            } catch (Throwable t) {
                LOG.debug("Cannot generate armor assets for {}: {}", id, t.toString());
            }
        }
    }

    private static Path findArmorTexture(Path dir, String base, int layer) throws IOException {
        if (!Files.isDirectory(dir)) {
            return null;
        }
        for (String name : List.of(base + "_" + layer + ".png", base + "_layer_" + layer + ".png",
                base + ".png")) {
            Path candidate = dir.resolve(name);
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static void writeBlockAssets(Path root, FabricBridge bridge) {
        for (Map.Entry<Identifier, dev.legacyloader.bridge.LegacyBlockView> entry : bridge.blocksById().entrySet()) {
            Identifier id = entry.getKey();
            dev.legacyloader.bridge.LegacyBlockView view = entry.getValue();
            try {
                Path assets = findAssets(root, id.getNamespace());
                if (assets == null) {
                    continue;
                }
                String path = id.getPath();
                Path namespaceRoot = assets.resolve(id.getNamespace());
                if (shouldSkipBlock(path)) {
                    writeInvisibleBlockState(namespaceRoot, path);
                    continue;
                }
                ensureBlockModel(namespaceRoot, id, path, view.legacyKind());
                Path modelPath = namespaceRoot.resolve("models").resolve("block").resolve(path + ".json");
                if (!Files.isRegularFile(modelPath)) {
                    writePlaceholderBlockModel(namespaceRoot, id);
                }
                writeBlockState(namespaceRoot, id, path, view.legacyKind());
            } catch (Throwable t) {
                LOG.debug("Cannot generate block assets for {}: {}", id, t.toString());
            }
        }
    }

    private static boolean shouldSkipBlock(String path) {
        return path.equals("air") || path.equals("barrier") || path.equals("empty")
                || path.equals("hole") || path.equals("portal")
                || path.startsWith("effect_") || path.startsWith("placeholder")
                || path.equals("flux_goo") || path.equals("liquid_death")
                || path.equals("purifying_fluid");
    }

    private static void writeBlockState(Path namespaceRoot, Identifier id, String path,
                                        dev.legacyloader.bridge.LegacyBlockKind kind) throws IOException {
        Path blockStates = namespaceRoot.resolve("blockstates");
        Files.createDirectories(blockStates);
        JsonObject variants = new JsonObject();
        if (kind == dev.legacyloader.bridge.LegacyBlockKind.SLAB) {
            for (String type : List.of("bottom", "top", "double")) {
                for (boolean waterlogged : List.of(false, true)) {
                    JsonObject variant = new JsonObject();
                    variant.addProperty("model", id.getNamespace() + ":block/" + path);
                    variants.add("type=" + type + ",waterlogged=" + waterlogged, variant);
                }
            }
        } else if (kind == dev.legacyloader.bridge.LegacyBlockKind.STAIRS) {
            for (String facing : List.of("north", "east", "south", "west")) {
                for (String half : List.of("bottom", "top")) {
                    for (String shape : List.of("straight", "inner_left", "inner_right", "outer_left", "outer_right")) {
                        for (boolean waterlogged : List.of(false, true)) {
                            JsonObject variant = new JsonObject();
                            variant.addProperty("model", id.getNamespace() + ":block/" + path);
                            variants.add("facing=" + facing + ",half=" + half + ",shape=" + shape
                                    + ",waterlogged=" + waterlogged, variant);
                        }
                    }
                }
            }
        } else {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", id.getNamespace() + ":block/" + path);
            variants.add("", variant);
        }
        JsonObject root = new JsonObject();
        root.add("variants", variants);
        Files.writeString(blockStates.resolve(path + ".json"),
                GSON.create().toJson(root), StandardCharsets.UTF_8);
    }

    private static void writeInvisibleBlockState(Path namespaceRoot, String path) throws IOException {
        writeVariant(namespaceRoot, path, "minecraft:block/air");
    }

    private static void writeVariant(Path namespaceRoot, String path, String model) throws IOException {
        Path blockStates = namespaceRoot.resolve("blockstates");
        Files.createDirectories(blockStates);
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model);
        JsonObject variants = new JsonObject();
        variants.add("", variant);
        JsonObject root = new JsonObject();
        root.add("variants", variants);
        Files.writeString(blockStates.resolve(path + ".json"),
                GSON.create().toJson(root), StandardCharsets.UTF_8);
    }

    private static void ensureBlockModel(Path namespaceRoot, Identifier id, String path,
                                         dev.legacyloader.bridge.LegacyBlockKind kind) throws IOException {
        if (kind == dev.legacyloader.bridge.LegacyBlockKind.SLAB
                || kind == dev.legacyloader.bridge.LegacyBlockKind.STAIRS) {
            Path texture = findBlockTexture(namespaceRoot, path);
            if (texture != null) {
                writeShapeModel(namespaceRoot, id, path, kind, texture);
                return;
            }
        }
        Path modelPath = namespaceRoot.resolve("models").resolve("block").resolve(path + ".json");
        if (Files.isRegularFile(modelPath)) {
            String existing = Files.readString(modelPath, StandardCharsets.UTF_8);
            if (!existing.contains("\"loader\"") && !existing.contains("forge_marker")) {
                return;
            }
        }

        JsonObject blockState = readJson(namespaceRoot.resolve("blockstates").resolve(path + ".json"));
        JsonObject model = new JsonObject();
        JsonObject textures = new JsonObject();

        if (blockState != null && blockState.has("forge_marker")) {
            JsonObject defaults = blockState.has("defaults") && blockState.get("defaults").isJsonObject()
                    ? blockState.getAsJsonObject("defaults") : null;
            if (defaults != null) {
                if (defaults.has("textures") && defaults.get("textures").isJsonObject()) {
                    textures = defaults.getAsJsonObject("textures").deepCopy();
                    normalizeTextures(textures);
                }
                String parent = modelName(defaults.get("model"));
                if (parent != null) {
                    model.addProperty("parent", qualifyParent(parent));
                }
            }
        }

        if (!model.has("parent") && blockState != null && blockState.has("variants")) {
            JsonObject variants = blockState.getAsJsonObject("variants");
            for (String key : variants.keySet()) {
                String parent = firstModel(variants.get(key));
                if (parent != null) {
                    model.addProperty("parent", qualifyParent(parent));
                    break;
                }
            }
        }

        if (!model.has("parent")) {
            Path texture = findBlockTexture(namespaceRoot, path);
            if (texture != null) {
                String parent = "minecraft:block/cube_all";
                model.addProperty("parent", parent);
                String base = id.getNamespace() + ":block/" + texture.getFileName().toString()
                        .replace(".png", "");
                textures.addProperty("all", base);
                textures.addProperty("particle", base);
            }
        }
        if (!model.has("parent")) {
            return;
        }
        if (!textures.isEmpty()) {
            model.add("textures", textures);
        }
        Files.createDirectories(modelPath.getParent());
        Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
    }

    private static JsonObject readJson(Path path) {
        if (!Files.isRegularFile(path)) {
            return null;
        }
        try {
            return com.google.gson.JsonParser.parseString(Files.readString(path, StandardCharsets.UTF_8))
                    .getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    private static String modelName(com.google.gson.JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        }
        if (element.isJsonObject()) {
            return modelName(element.getAsJsonObject().get("model"));
        }
        return null;
    }

    private static String firstModel(com.google.gson.JsonElement element) {
        if (element == null) {
            return null;
        }
        if (element.isJsonArray()) {
            for (com.google.gson.JsonElement item : element.getAsJsonArray()) {
                String model = firstModel(item);
                if (model != null) {
                    return model;
                }
            }
            return null;
        }
        if (element.isJsonObject()) {
            return modelName(element.getAsJsonObject().get("model"));
        }
        return null;
    }

    private static String qualifyParent(String parent) {
        return parent.contains(":") ? parent : "minecraft:block/" + parent;
    }

    private static void writeShapeModel(Path namespaceRoot, Identifier id, String path,
                                        dev.legacyloader.bridge.LegacyBlockKind kind,
                                        Path texture) throws IOException {
        String base = id.getNamespace() + ":block/" + texture.getFileName().toString()
                .replace(".png", "");
        JsonObject model = new JsonObject();
        String parent = kind == dev.legacyloader.bridge.LegacyBlockKind.SLAB
                ? "minecraft:block/slab" : "minecraft:block/stairs";
        model.addProperty("parent", parent);
        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", base);
        textures.addProperty("top", base);
        textures.addProperty("side", base);
        textures.addProperty("all", base);
        textures.addProperty("particle", base);
        model.add("textures", textures);
        Path modelPath = namespaceRoot.resolve("models").resolve("block").resolve(path + ".json");
        Files.createDirectories(modelPath.getParent());
        Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
    }

    private static Path findBlockTexture(Path namespaceRoot, String path) throws IOException {
        Path textures = namespaceRoot.resolve("textures").resolve("block");
        Path exact = textures.resolve(path + ".png");
        if (Files.isRegularFile(exact)) {
            return exact;
        }
        String base = path;
        if (base.startsWith("slab_")) {
            base = base.substring("slab_".length());
        } else if (base.startsWith("stairs_")) {
            base = base.substring("stairs_".length());
        }
        Path baseExact = textures.resolve(base + ".png");
        if (Files.isRegularFile(baseExact)) {
            return baseExact;
        }
        if (!Files.isDirectory(textures)) {
            return null;
        }
        for (String prefix : List.of(base + "_", path + "_")) {
            try (var stream = Files.list(textures)) {
                Optional<Path> candidate = stream
                        .filter(Files::isRegularFile)
                        .filter(file -> file.getFileName().toString().startsWith(prefix)
                                && file.getFileName().toString().endsWith(".png"))
                        .sorted()
                        .findFirst();
                if (candidate.isPresent()) {
                    return candidate.get();
                }
            }
        }
        return null;
    }

    private static void normalizeTextures(JsonObject textures) {
        for (String key : textures.keySet()) {
            if (!textures.get(key).isJsonPrimitive()) {
                continue;
            }
            String value = textures.get(key).getAsString()
                    .replace(":blocks/", ":block/")
                    .replace(":items/", ":item/")
                    .replace(":models/", ":block/")
                    .replace("minecraft:block/fire_layer_0", "minecraft:block/fire_0")
                    .replace("minecraft:block/fire_layer_1", "minecraft:block/fire_1");
            textures.addProperty(key, value);
        }
    }

    private static String synthesizeItemModel(Path namespaceRoot, Identifier id) throws IOException {
        Path texture = findItemTexture(namespaceRoot, id.getPath());
        if (texture == null) {
            return null;
        }
        Path modelPath = namespaceRoot.resolve("models").resolve("item").resolve(id.getPath() + ".json");
        if (!Files.isRegularFile(modelPath)) {
            Files.createDirectories(modelPath.getParent());
            JsonObject model = new JsonObject();
            model.addProperty("parent", "minecraft:item/generated");
            JsonObject textures = new JsonObject();
            String folder = texture.getParent().getFileName().toString();
            String file = texture.getFileName().toString();
            textures.addProperty("layer0", id.getNamespace() + ":" + folder + "/"
                    + file.substring(0, file.length() - ".png".length()));
            model.add("textures", textures);
            Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
        }
        return id.getNamespace() + ":item/" + id.getPath();
    }

    private static String writePlaceholderItemModel(Path namespaceRoot, Identifier id) throws IOException {
        ensurePlaceholderTexture(namespaceRoot);
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", id.getNamespace() + ":item/legacy_missing");
        model.add("textures", textures);
        Path modelPath = namespaceRoot.resolve("models").resolve("item").resolve(id.getPath() + ".json");
        Files.createDirectories(modelPath.getParent());
        Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
        return id.getNamespace() + ":item/" + id.getPath();
    }

    private static void writePlaceholderBlockModel(Path namespaceRoot, Identifier id) throws IOException {
        ensurePlaceholderTexture(namespaceRoot);
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:block/cube_all");
        JsonObject textures = new JsonObject();
        textures.addProperty("all", id.getNamespace() + ":block/legacy_missing");
        model.add("textures", textures);
        Path modelPath = namespaceRoot.resolve("models").resolve("block").resolve(id.getPath() + ".json");
        Files.createDirectories(modelPath.getParent());
        Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
    }

    private static void ensurePlaceholderTexture(Path namespaceRoot) throws IOException {
        Path item = namespaceRoot.resolve("textures").resolve("item").resolve("legacy_missing.png");
        Path block = namespaceRoot.resolve("textures").resolve("block").resolve("legacy_missing.png");
        if (Files.isRegularFile(item) && Files.isRegularFile(block)) {
            return;
        }
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(16, 16,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int color = ((x / 4 + y / 4) % 2 == 0) ? 0xFF3A0A4A : 0xFF160018;
                image.setRGB(x, y, color);
            }
        }
        Files.createDirectories(item.getParent());
        Files.createDirectories(block.getParent());
        javax.imageio.ImageIO.write(image, "png", item.toFile());
        javax.imageio.ImageIO.write(image, "png", block.toFile());
    }

    private static Path findItemTexture(Path namespaceRoot, String path) throws IOException {        Path textures = namespaceRoot.resolve("textures");
        for (String folder : List.of("item", "block")) {
            Path exact = textures.resolve(folder).resolve(path + ".png");
            if (Files.isRegularFile(exact)) {
                return exact;
            }
        }
        for (String folder : List.of("item", "block")) {
            Path dir = textures.resolve(folder);
            if (!Files.isDirectory(dir)) {
                continue;
            }
            try (var stream = Files.list(dir)) {
                Optional<Path> candidate = stream
                        .filter(Files::isRegularFile)
                        .filter(file -> {
                            String name = file.getFileName().toString();
                            return name.startsWith(path + "_") && name.endsWith(".png");
                        })
                        .sorted()
                        .findFirst();
                if (candidate.isPresent()) {
                    return candidate.get();
                }
            }
        }
        return null;
    }

    private static Path findAssets(Path root, String namespace) throws IOException {
        Path direct = root.resolve(namespace);
        if (Files.isDirectory(direct.resolve("assets").resolve(namespace))) {
            return direct.resolve("assets");
        }
        if (!Files.isDirectory(root)) {
            return null;
        }
        try (var stream = Files.list(root)) {
            for (Path candidate : stream.toList()) {
                if (Files.isDirectory(candidate.resolve("assets").resolve(namespace))) {
                    return candidate.resolve("assets");
                }
            }
        }
        return null;
    }

    private static void extractAssets(Path jarPath, Path packDir) throws IOException {
        Path marker = packDir.resolve(".legacyloader.stamp");
        String stamp = GENERATOR_VERSION + ":" + Files.getLastModifiedTime(jarPath).toMillis()
                + ":" + Files.size(jarPath);
        if (Files.isRegularFile(marker) && stamp.equals(Files.readString(marker, StandardCharsets.UTF_8))) {
            return;
        }
        Files.createDirectories(packDir);
        Path assets = packDir.resolve("assets");
        if (Files.isDirectory(assets)) {
            deleteRecursively(assets);
        }
        try (JarFile jar = new JarFile(jarPath.toFile(), false)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || !name.startsWith("assets/")) {
                    continue;
                }
                if (name.contains("..")) {
                    continue;
                }
                Path target = packDir.resolve(name).normalize();
                if (!target.startsWith(packDir)) {
                    continue;
                }
                Files.createDirectories(target.getParent());
                try (InputStream in = jar.getInputStream(entry)) {
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        Files.writeString(marker, stamp, StandardCharsets.UTF_8);
    }

    /**
     * Modern atlases only scan {@code textures/block} and {@code textures/item}.
     * 1.12 uses plural directories, so mirror them and rewrite model texture
     * references to the singular paths.
     */
    private static void migrateTextures(Path packDir) throws IOException {
        Path assets = packDir.resolve("assets");
        if (!Files.isDirectory(assets)) {
            return;
        }
        try (var namespaces = Files.list(assets)) {
            for (Path namespace : namespaces.toList()) {
                if (!Files.isDirectory(namespace)) {
                    continue;
                }
                mirrorDirectory(namespace.resolve("textures").resolve("blocks"),
                        namespace.resolve("textures").resolve("block"));
                mirrorDirectory(namespace.resolve("textures").resolve("items"),
                        namespace.resolve("textures").resolve("item"));
                // Old chest/entity-style atlases lived under textures/models and
                // were referenced as materials by block models.
                mirrorDirectory(namespace.resolve("textures").resolve("models"),
                        namespace.resolve("textures").resolve("block"));
                mirrorDirectory(namespace.resolve("textures").resolve("entity").resolve("armor"),
                        namespace.resolve("textures").resolve("item"));
                rewriteModelTextures(namespace.resolve("models"));
                normalizeModelParents(namespace.resolve("models"));
                fixModelParticles(namespace.resolve("models"));
                fixLegacyItemModels(namespace.resolve("models").resolve("item"), namespace.getFileName().toString());
            }
        }
    }

    private static void mirrorDirectory(Path source, Path target) throws IOException {
        if (!Files.isDirectory(source)) {
            return;
        }
        try (var stream = Files.walk(source)) {
            for (Path path : stream.toList()) {
                if (!Files.isRegularFile(path)) {
                    continue;
                }
                Path relative = source.relativize(path);
                Path destination = target.resolve(relative);
                Files.createDirectories(destination.getParent());
                Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void rewriteModelTextures(Path models) throws IOException {
        if (!Files.isDirectory(models)) {
            return;
        }
        try (var stream = Files.walk(models)) {
            for (Path model : stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .toList()) {
                String text = Files.readString(model, StandardCharsets.UTF_8);
                String rewritten = text.replace(":blocks/", ":block/")
                        .replace(":items/", ":item/")
                        .replace(":models/", ":block/")
                        .replace(":entity/armor/", ":item/")
                        .replace("\"blocks/", "\"block/")
                        .replace("\"items/", "\"item/")
                        .replace("\"models/", "\"block/")
                        .replace("\"entity/armor/", "\"item/")
                        .replace("block/fire_layer_0", "block/fire_0")
                        .replace("block/fire_layer_1", "block/fire_1")
                        .replace("block/stone_slab_top", "block/smooth_stone")
                        .replace("block/stone_slab_side", "block/smooth_stone_slab_side");
                if (!rewritten.equals(text)) {
                    Files.writeString(model, rewritten, StandardCharsets.UTF_8);
                }
            }
        }
    }

    private static void fixModelParticles(Path models) throws IOException {
        if (!Files.isDirectory(models)) {
            return;
        }
        try (var stream = Files.walk(models)) {
            for (Path modelPath : stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .toList()) {
                JsonObject model = readJson(modelPath);
                if (model == null || !model.has("textures") || !model.get("textures").isJsonObject()) {
                    continue;
                }
                JsonObject textures = model.getAsJsonObject("textures");
                if (textures.isEmpty()) {
                    continue;
                }
                String first = textures.entrySet().iterator().next().getValue().getAsString();
                String text = GSON.create().toJson(model);
                for (String key : List.of("particle", "down", "up", "north", "south", "east", "west", "all")) {
                    if (text.contains("#" + key) && !textures.has(key)) {
                        textures.addProperty(key, first);
                    }
                }
                Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
            }
        }
    }

    private static void normalizeModelParents(Path models) throws IOException {
        if (!Files.isDirectory(models)) {
            return;
        }
        try (var stream = Files.walk(models)) {
            for (Path modelPath : stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .toList()) {
                JsonObject model = readJson(modelPath);
                if (model == null || !model.has("parent") || !model.get("parent").isJsonPrimitive()) {
                    continue;
                }
                String parent = model.get("parent").getAsString();
                String rewritten = qualifyLegacyParent(models, parent);
                if (!rewritten.equals(parent)) {
                    model.addProperty("parent", rewritten);
                    Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
                }
            }
        }
    }

    private static String qualifyLegacyParent(Path models, String parent) {
        String namespace = "minecraft";
        String path = parent;
        if (parent.contains(":")) {
            namespace = parent.substring(0, parent.indexOf(':'));
            path = parent.substring(parent.indexOf(':') + 1);
        }
        if (path.contains("/")) {
            return parent;
        }
        Path block = models.resolve("block").resolve(path + ".json");
        if (Files.isRegularFile(block)) {
            return namespace + ":block/" + path;
        }
        Path item = models.resolve("item").resolve(path + ".json");
        if (Files.isRegularFile(item)) {
            return namespace + ":item/" + path;
        }
        if (!parent.contains(":")) {
            return "minecraft:block/" + parent;
        }
        return parent;
    }

    private static void fixLegacyItemModels(Path itemModels, String namespace) throws IOException {
        if (!Files.isDirectory(itemModels)) {
            return;
        }
        try (var stream = Files.list(itemModels)) {
            for (Path modelPath : stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .toList()) {
                JsonObject model = readJson(modelPath);
                if (model == null || model.has("textures") || !model.has("parent")) {
                    continue;
                }
                String parent = model.get("parent").getAsString();
                boolean generated = parent.equals("item/generated") || parent.equals("minecraft:item/generated");
                if (!generated) {
                    continue;
                }
                JsonObject textures = null;
                if (model.has("overrides") && model.get("overrides").isJsonArray()) {
                    for (com.google.gson.JsonElement element : model.getAsJsonArray("overrides")) {
                        if (!element.isJsonObject()) {
                            continue;
                        }
                        String override = modelName(element.getAsJsonObject().get("model"));
                        if (override == null) {
                            continue;
                        }
                        Path overridePath = itemModels.resolve(
                                override.substring(override.indexOf('/') + 1) + ".json");
                        JsonObject overrideModel = readJson(overridePath);
                        if (overrideModel != null && overrideModel.has("textures")) {
                            textures = overrideModel.getAsJsonObject("textures").deepCopy();
                            break;
                        }
                    }
                }
                if (textures == null || textures.isEmpty()) {
                    String name = modelPath.getFileName().toString();
                    name = name.substring(0, name.length() - ".json".length());
                    Path texture = findItemTexture(itemModels.getParent().getParent(), name);
                    if (texture != null) {
                        textures = new JsonObject();
                        String folder = texture.getParent().getFileName().toString();
                        String file = texture.getFileName().toString();
                        textures.addProperty("layer0", namespace + ":" + folder + "/"
                                + file.substring(0, file.length() - ".png".length()));
                    }
                }
                if (textures != null && !textures.isEmpty()) {
                    model.add("textures", textures);
                    model.remove("overrides");
                    Files.writeString(modelPath, GSON.create().toJson(model), StandardCharsets.UTF_8);
                }
            }
        }
    }

    /**
     * Modern resource identifiers are case-sensitive and must be lowercase.
     * 1.12 mods occasionally ship mixed-case texture files; rename them and
     * lowercase model/blockstate JSON so references keep matching.
     */
    private static void lowercaseTree(Path root) throws IOException {
        if (!Files.isDirectory(root)) {
            return;
        }
        List<Path> paths;
        try (var stream = Files.walk(root)) {
            paths = stream.sorted(java.util.Comparator.reverseOrder()).toList();
        }
        for (Path path : paths) {
            String name = path.getFileName().toString();
            String lower = name.toLowerCase(java.util.Locale.ROOT);
            if (!name.equals(lower)) {
                Path target = path.resolveSibling(lower);
                if (!Files.exists(target)) {
                    Files.move(path, target);
                } else if (Files.isRegularFile(path)) {
                    Files.deleteIfExists(path);
                }
            }
        }
        try (var stream = Files.walk(root)) {
            for (Path json : stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".json")
                            && !path.toString().contains("/lang/"))
                    .toList()) {
                String text = Files.readString(json, StandardCharsets.UTF_8);
                String lowered = text.toLowerCase(java.util.Locale.ROOT);
                if (!lowered.equals(text)) {
                    Files.writeString(json, lowered, StandardCharsets.UTF_8);
                }
            }
        }
    }

    private static void convertLanguages(Path packDir) throws IOException {        for (Path lang : findFiles(packDir.resolve("assets"), ".lang")) {
            Path relative = packDir.resolve("assets").relativize(lang);
            Path json = lang.resolveSibling(lang.getFileName().toString()
                    .replaceFirst("\\.lang$", ".json"));
            Map<String, String> entries = new LinkedHashMap<>();
            for (String line : Files.readAllLines(lang, StandardCharsets.UTF_8)) {
                String trimmed = line.strip();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int eq = line.indexOf('=');
                if (eq <= 0) {
                    continue;
                }
                entries.put(line.substring(0, eq).strip(), line.substring(eq + 1));
            }
            JsonObject object = new JsonObject();
            entries.forEach(object::addProperty);
            Files.writeString(json, GSON.create().toJson(object), StandardCharsets.UTF_8);
        }
    }

    private static List<Path> findFiles(Path root, String suffix) throws IOException {
        List<Path> result = new ArrayList<>();
        if (!Files.isDirectory(root)) {
            return result;
        }
        try (var stream = Files.walk(root)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(suffix))
                    .forEach(result::add);
        }
        return result;
    }

    private static void writePackMeta(Path packDir, String name) throws IOException {
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", SharedConstants.RESOURCE_PACK_FORMAT_MAJOR);
        pack.addProperty("min_format", SharedConstants.RESOURCE_PACK_FORMAT_MAJOR);
        pack.addProperty("max_format", SharedConstants.RESOURCE_PACK_FORMAT_MAJOR);
        pack.addProperty("description", "Legacy assets: " + name);
        JsonObject root = new JsonObject();
        root.add("pack", pack);
        Files.writeString(packDir.resolve("pack.mcmeta"), GSON.create().toJson(root), StandardCharsets.UTF_8);
    }

    private static boolean registerPack(Path packDir, LegacyModContainer container) {
        Identifier id = Identifier.fromNamespaceAndPath("legacyloader",
                container.modId().toLowerCase() + "_assets");
        ModContainer holder = new PackModContainer(packDir, container);
        boolean registered = ResourceLoader.registerBuiltinPack(id, "", holder, PackActivationType.ALWAYS_ENABLED);
        if (registered) {
            LOG.info("Registered legacy resource pack {} for {} at {}",
                    id, container.modId(), packDir);
        }
        return registered;
    }

    private static void deleteRecursively(Path path) throws IOException {
        try (var stream = Files.walk(path)) {
            for (Path entry : stream.sorted(java.util.Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(entry);
            }
        }
    }

    private static final class PackModContainer implements ModContainer {
        private final Path root;
        private final LegacyModContainer legacy;
        private final ModMetadata metadata;

        private PackModContainer(Path root, LegacyModContainer legacy) {
            this.root = root;
            this.legacy = legacy;
            this.metadata = metadata(legacy);
        }

        @Override
        public ModMetadata getMetadata() {
            return metadata;
        }

        @Override
        public List<Path> getRootPaths() {
            return List.of(root);
        }

        @Override
        public net.fabricmc.loader.api.metadata.ModOrigin getOrigin() {
            return null;
        }

        @Override
        public Optional<ModContainer> getContainingMod() {
            return Optional.empty();
        }

        @Override
        public java.util.Collection<ModContainer> getContainedMods() {
            return List.of();
        }

        @Override
        public Path getRootPath() {
            return root;
        }

        @Override
        public Path getPath(String path) {
            return root.resolve(path);
        }

        private static ModMetadata metadata(LegacyModContainer legacy) {
            return (ModMetadata) Proxy.newProxyInstance(
                    ModMetadata.class.getClassLoader(),
                    new Class<?>[]{ModMetadata.class},
                    (proxy, method, args) -> switch (method.getName()) {
                        case "getId" -> legacy.modId();
                        case "getName" -> legacy.modName();
                        case "getType" -> "legacy";
                        case "getVersion" -> parseVersion(legacy.modVersion());
                        case "getEnvironment" -> ModEnvironment.UNIVERSAL;
                        case "getDescription" -> "";
                        case "getAuthors", "getContributors", "getLicense", "getProvides", "getDependencies" ->
                                List.of();
                        case "getContact" -> ContactInformation.EMPTY;
                        case "getIconPath" -> Optional.empty();
                        case "containsCustomValue", "containsCustomElement" -> false;
                        case "getCustomValue" -> null;
                        case "getCustomValues" -> Map.of();
                        case "toString" -> "LegacyModMetadata[" + legacy.modId() + "]";
                        case "hashCode" -> legacy.modId().hashCode();
                        case "equals" -> proxy == args[0];
                        default -> defaultValue(method.getReturnType());
                    });
        }

        private static Version parseVersion(String version) {
            try {
                return Version.parse(version);
            } catch (Exception e) {
                try {
                    return Version.parse("1.0.0");
                } catch (Exception ignored) {
                    return null;
                }
            }
        }

        private static Object defaultValue(Class<?> type) {
            if (type == boolean.class) {
                return false;
            }
            if (type == int.class) {
                return 0;
            }
            if (type == long.class) {
                return 0L;
            }
            if (type == float.class) {
                return 0.0F;
            }
            if (type == double.class) {
                return 0.0D;
            }
            if (type == Optional.class) {
                return Optional.empty();
            }
            if (List.class.isAssignableFrom(type)) {
                return List.of();
            }
            if (Map.class.isAssignableFrom(type)) {
                return Map.of();
            }
            return null;
        }
    }
}
