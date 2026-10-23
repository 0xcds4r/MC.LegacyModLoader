package dev.legacyloader.discovery;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Forge 1.12.2 dependency declarations, normalized.
 */
public record ModDependencies(Set<String> required, Set<String> after, Set<String> before) {
    private static final Set<String> PLATFORM = Set.of("minecraft", "forge", "fml", "java");

    public ModDependencies {
        required = Set.copyOf(required);
        after = Set.copyOf(after);
        before = Set.copyOf(before);
    }

    public static ModDependencies empty() {
        return new ModDependencies(Set.of(), Set.of(), Set.of());
    }

    /**
     * Accepts both the {@code mcmod.info} list (bare mod ids) and the
     * {@code @Mod(dependencies = "...")} mini-language.
     */
    public static ModDependencies parse(List<String> mcmodDependencies, String annotationDependencies) {
        Set<String> required = new LinkedHashSet<>();
        Set<String> after = new LinkedHashSet<>();
        Set<String> before = new LinkedHashSet<>();

        for (String dependency : mcmodDependencies) {
            addRequired(required, dependency);
        }

        if (annotationDependencies != null && !annotationDependencies.isBlank()) {
            for (String token : annotationDependencies.split(";")) {
                String spec = token.strip();
                if (spec.isEmpty()) {
                    continue;
                }
                String kind;
                String value;
                int colon = spec.indexOf(':');
                if (colon < 0) {
                    kind = "required";
                    value = spec;
                } else {
                    kind = spec.substring(0, colon).strip().toLowerCase();
                    value = spec.substring(colon + 1).strip();
                }
                switch (kind) {
                    case "required-after" -> {
                        addRequired(required, value);
                        addId(after, value);
                    }
                    case "required-before" -> {
                        addRequired(required, value);
                        addId(before, value);
                    }
                    case "after" -> addId(after, value);
                    case "before" -> addId(before, value);
                    case "required" -> addRequired(required, value);
                    case "optional", "optional-after" -> addId(after, value);
                    default -> {
                        // Unknown directives are ignored on purpose: old mods
                        // frequently carry stale loader-specific tokens.
                    }
                }
            }
        }
        return new ModDependencies(required, after, before);
    }

    private static void addRequired(Set<String> target, String raw) {
        String id = normalize(raw);
        if (!id.isEmpty() && !PLATFORM.contains(id)) {
            target.add(id);
        }
    }

    private static void addId(Set<String> target, String raw) {
        String id = normalize(raw);
        if (!id.isEmpty() && !PLATFORM.contains(id)) {
            target.add(id);
        }
    }

    private static String normalize(String raw) {
        String value = raw.strip();
        int at = value.indexOf('@');
        if (at >= 0) {
            value = value.substring(0, at);
        }
        int range = value.indexOf('[');
        if (range >= 0) {
            value = value.substring(0, range);
        }
        return value.strip().toLowerCase();
    }

    public boolean isEmpty() {
        return required.isEmpty() && after.isEmpty() && before.isEmpty();
    }
}
