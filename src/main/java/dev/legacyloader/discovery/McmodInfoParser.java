package dev.legacyloader.discovery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Parses the 1.12.2 metadata formats without loading any mod code.
 */
public final class McmodInfoParser {
    private McmodInfoParser() {
    }

    public static McmodInfo read(JarFile jar, String fallbackId) {
        JsonObject object = readRootObject(jar);
        if (object == null) {
            return McmodInfo.unknown(fallbackId);
        }
        return fromJson(object, fallbackId);
    }

    private static JsonObject readRootObject(JarFile jar) {
        for (String candidate : List.of("mcmod.info", "META-INF/mcmod.info")) {
            JarEntry entry = jar.getJarEntry(candidate);
            if (entry == null) {
                continue;
            }
            try (InputStreamReader reader = new InputStreamReader(jar.getInputStream(entry), StandardCharsets.UTF_8)) {
                JsonElement root = JsonParser.parseReader(reader);
                if (root.isJsonArray()) {
                    JsonArray array = root.getAsJsonArray();
                    if (array.isEmpty()) {
                        return null;
                    }
                    root = array.get(0);
                }
                if (root.isJsonObject()) {
                    return root.getAsJsonObject();
                }
            } catch (IOException | RuntimeException ignored) {
                // fall through to the next candidate
            }
        }
        return null;
    }

    private static McmodInfo fromJson(JsonObject object, String fallbackId) {
        String modId = string(object, "modid", fallbackId);
        String name = string(object, "name", modId);
        String version = string(object, "version", "unknown");
        String mcVersion = string(object, "mcversion", "");
        String description = string(object, "description", "");
        List<String> authors = stringList(object, "authorList");
        List<String> dependencies = stringList(object, "dependencies");
        boolean clientOnly = bool(object, "clientSideOnly");
        boolean serverOnly = bool(object, "serverSideOnly");
        String updateJson = string(object, "updateJSON", "");
        String parent = string(object, "parent", "");
        return new McmodInfo(modId, name, version, mcVersion, description, authors,
                dependencies, clientOnly, serverOnly, updateJson, parent);
    }

    private static String string(JsonObject object, String key, String fallback) {
        JsonElement element = object.get(key);
        if (element == null || element.isJsonNull() || !element.isJsonPrimitive()) {
            return fallback;
        }
        String value = element.getAsString();
        return value.isEmpty() ? fallback : value;
    }

    private static boolean bool(JsonObject object, String key) {
        JsonElement element = object.get(key);
        return element != null && element.isJsonPrimitive() && element.getAsBoolean();
    }

    private static List<String> stringList(JsonObject object, String key) {
        JsonElement element = object.get(key);
        if (element == null || element.isJsonNull()) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        if (element.isJsonArray()) {
            for (JsonElement item : element.getAsJsonArray()) {
                if (item.isJsonPrimitive()) {
                    result.add(item.getAsString());
                }
            }
        } else if (element.isJsonPrimitive()) {
            result.add(element.getAsString());
        }
        return result;
    }
}
