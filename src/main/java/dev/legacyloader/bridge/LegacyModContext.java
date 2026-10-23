package dev.legacyloader.bridge;

import java.nio.file.Path;

/**
 * Narrow context handed to shim code. Only bridge types appear here, never
 * modern Minecraft classes, so implementing classes can live outside the shim
 * classloader without leaking the modern class hierarchy into legacy code.
 */
public interface LegacyModContext {
    String modId();

    String modName();

    String modVersion();

    LegacySide side();

    Path configDirectory();

    Path gameDirectory();

    String loggerName();

    Object modInstance();

    ClassLoader modClassLoader();

    /**
     * Absolute path of the original, untouched mod jar. Legacy mods are allowed
     * to read it; the loader never writes to it.
     */
    Path sourceJar();
}
