package dev.legacyloader.classloader;

import java.util.List;

/**
 * Decides what a legacy class is allowed to see. The decision is made from the
 * binary name before anything is loaded, so a denied name can never end up in
 * the legacy loader as a side effect of resolution.
 */
public final class ClassRules {
    public enum Visibility {
        /** JDK classes, delegated to the platform classloader. */
        PLATFORM,
        /** Legacy API classes shipped by the loader. */
        SHIM,
        /** Third-party libraries deliberately shared with legacy code. */
        SHARED,
        /** Ordinary class: resolved child-first from the mod jar and dependencies. */
        MOD,
        /** Modern game/loader classes: never visible. */
        BLOCKED
    }

    private static final List<String> BLOCKED_PREFIXES = List.of(
            "net.minecraft.",
            "net.minecraftforge.",
            "net.neoforged.",
            "net.fabricmc.",
            "com.mojang.",
            "org.lwjgl.",
            "org.objectweb.asm.",
            "org.jetbrains.",
            "jdk.internal."
    );

    private static final List<String> PLATFORM_PREFIXES = List.of(
            "java.",
            "javax.",
            "jdk."
    );

    /**
     * Libraries old mods link against directly. Sharing them is a pragmatic
     * compromise: they are not game API, they are broadly binary compatible,
     * and shimming Guava/Gson would be a project of its own.
     */
    private static final List<String> SHARED_PREFIXES = List.of(
            "org.apache.logging.log4j.",
            "org.slf4j.",
            "com.google.gson.",
            "com.google.common.",
            "it.unimi.dsi.fastutil.",
            "javax.annotation.",
            "sun.misc.",
            "io.netty."
    );

    private final ShimIndex shims;

    public ClassRules(ShimIndex shims) {
        this.shims = shims;
    }

    public Visibility classify(String binaryName) {
        // Shim names are checked first: they intentionally live in blocked
        // namespaces such as net.minecraft.* and net.minecraftforge.*.
        if (shims.contains(binaryName)) {
            return Visibility.SHIM;
        }
        if (startsWithAny(binaryName, BLOCKED_PREFIXES)) {
            return Visibility.BLOCKED;
        }
        if (startsWithAny(binaryName, PLATFORM_PREFIXES)) {
            return Visibility.PLATFORM;
        }
        if (startsWithAny(binaryName, SHARED_PREFIXES)) {
            return Visibility.SHARED;
        }
        return Visibility.MOD;
    }

    public boolean isShim(String binaryName) {
        return shims.contains(binaryName);
    }

    public ShimIndex shims() {
        return shims;
    }

    private static boolean startsWithAny(String value, List<String> prefixes) {
        for (String prefix : prefixes) {
            if (value.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
