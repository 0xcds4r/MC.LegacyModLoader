package dev.legacyloader.discovery;

import java.util.List;

/**
 * Normalized {@code mcmod.info} entry. Forge 1.12.2 allows the file to contain
 * a single object or an array; both are accepted by the parser.
 */
public record McmodInfo(
        String modId,
        String name,
        String version,
        String mcVersion,
        String description,
        List<String> authors,
        List<String> declaredDependencies,
        boolean clientSideOnly,
        boolean serverSideOnly,
        String updateJson,
        String parent
) {
    public McmodInfo {
        authors = List.copyOf(authors);
        declaredDependencies = List.copyOf(declaredDependencies);
    }

    public static McmodInfo unknown(String fallbackId) {
        return new McmodInfo(
                fallbackId,
                fallbackId,
                "unknown",
                "",
                "No mcmod.info found",
                List.of(),
                List.of(),
                false,
                false,
                "",
                ""
        );
    }
}
