package dev.legacyloader.bridge;

/**
 * Side as seen by a legacy mod. Deliberately duplicated from modern loader
 * concepts so shims never link against Fabric or NeoForge classes.
 */
public enum LegacySide {
    CLIENT,
    SERVER
}
