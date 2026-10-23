package dev.legacyloader.classloader;

import dev.legacyloader.api.LegacyProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Whitelist of shim classes shipped by the loader.
 *
 * <p>The index is generated at build time from the {@code shim1122} source set
 * and stored as a resource. It must never be derived from "what the platform
 * classloader happens to resolve": that would turn every modern Minecraft class
 * into a visible API, which is exactly what we are trying to prevent.
 */
public final class ShimIndex {
    private final String profileId;
    private final Set<String> binaryNames;

    private ShimIndex(String profileId, Set<String> binaryNames) {
        this.profileId = profileId;
        this.binaryNames = Collections.unmodifiableSet(binaryNames);
    }

    public static ShimIndex load(LegacyProfile profile) {
        String resource = profile.shimIndexResource();
        Set<String> names = new LinkedHashSet<>();
        try (InputStream in = ShimIndex.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Missing shim index resource: " + resource);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String name = line.strip();
                    if (!name.isEmpty() && !name.startsWith("#")) {
                        names.add(name.replace('/', '.'));
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read shim index " + resource, e);
        }
        return new ShimIndex(profile.id(), names);
    }

    public boolean contains(String binaryName) {
        return binaryNames.contains(binaryName);
    }

    public Set<String> names() {
        return binaryNames;
    }

    public int size() {
        return binaryNames.size();
    }

    @Override
    public String toString() {
        return "ShimIndex[" + profileId + ", " + binaryNames.size() + " classes]";
    }
}
