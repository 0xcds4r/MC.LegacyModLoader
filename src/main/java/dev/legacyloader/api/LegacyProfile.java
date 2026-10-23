package dev.legacyloader.api;

/**
 * A compatibility profile describes one historical modding platform
 * (1.12.2 Forge, 1.7.10 Forge, 1.16.5 Forge ...). Everything version specific
 * is behind this interface; the runtime, discovery and classloader code is not.
 */
public interface LegacyProfile {
    /** Stable id, for example {@code "forge-1.12.2"}. */
    String id();

    /** Human readable platform version, for example {@code "1.12.2"}. */
    String minecraftVersion();

    /** Classpath resource containing one binary class name per line. */
    String shimIndexResource();

    /** Binary name of the mod annotation, e.g. {@code net.minecraftforge.fml.common.Mod}. */
    String modAnnotationClass();

    /** Binary name of the lifecycle handler annotation. */
    String eventHandlerAnnotationClass();

    /** Binary name of the event class passed to a lifecycle phase, or {@code null}. */
    String lifecycleEventClass(LifecyclePhase phase);

    /**
     * Resource directory containing {@code methods.csv} and {@code fields.csv}
     * (SRG to MCP), or {@code null} when the target is already deobfuscated.
     */
    default String srgMappingsDirectory() {
        return null;
    }

    /**
     * Mod ids whose API is emulated by this compatibility profile rather than
     * supplied as a legacy jar. Used by dependency resolution and shown as
     * degraded in the boot report.
     */
    default java.util.Set<String> providedMods() {
        return java.util.Set.of();
    }

    /**
     * Returns true when the metadata claims a Minecraft version this profile can
     * host. Implementations should accept patch-level variants such as 1.12.2
     * when the profile targets 1.12.x only if the ABI is known to be compatible.
     */
    boolean acceptsMinecraftVersion(String mcVersion);
}
