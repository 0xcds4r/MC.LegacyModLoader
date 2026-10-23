package dev.legacyloader.discovery;

import java.nio.file.Path;
import java.util.Objects;

/**
 * One jar found in {@code old_mods/}, with metadata and resolved load order.
 */
public final class DiscoveredMod {
    private final Path jarPath;
    private final McmodInfo info;
    private final String modClass;
    private final ModDependencies dependencies;
    private String disabledReason;

    public DiscoveredMod(Path jarPath, McmodInfo info, String modClass, ModDependencies dependencies) {
        this.jarPath = Objects.requireNonNull(jarPath);
        this.info = Objects.requireNonNull(info);
        this.modClass = modClass;
        this.dependencies = Objects.requireNonNull(dependencies);
    }

    public Path jarPath() {
        return jarPath;
    }

    public McmodInfo info() {
        return info;
    }

    public String modId() {
        return info.modId();
    }

    public String modClass() {
        return modClass;
    }

    public ModDependencies dependencies() {
        return dependencies;
    }

    public boolean hasEntryPoint() {
        return modClass != null;
    }

    public boolean isEnabled() {
        return disabledReason == null;
    }

    public String disabledReason() {
        return disabledReason;
    }

    public void disable(String reason) {
        if (disabledReason == null) {
            disabledReason = reason;
        }
    }

    @Override
    public String toString() {
        return "DiscoveredMod[" + modId() + " " + info.version() + " " + jarPath.getFileName() + "]";
    }
}
