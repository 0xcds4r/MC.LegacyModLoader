package dev.legacyloader.bridge;

/**
 * Read-only projection of a legacy {@code net.minecraft.util.SoundEvent}.
 */
public interface LegacySoundView {
    String legacySoundNamespace();

    String legacySoundPath();
}
