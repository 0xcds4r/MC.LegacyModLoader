package net.minecraft.util;

/**
 * 1.12.2 resource location shim. Modern versions renamed this type to
 * {@code net.minecraft.resources.Identifier}; the old name is free in 26.3,
 * which is exactly why the shim can keep the original binary name.
 */
public class ResourceLocation implements Comparable<ResourceLocation> {
    private final String namespace;
    private final String path;

    public ResourceLocation(String combined) {
        this(splitNamespace(combined), splitPath(combined));
    }

    public ResourceLocation(String namespace, String path) {
        this.namespace = namespace == null || namespace.isEmpty() ? "minecraft" : namespace;
        this.path = path == null ? "" : path;
    }

    public static ResourceLocation tryParse(String combined) {
        if (combined == null || combined.isEmpty()) {
            return null;
        }
        return new ResourceLocation(combined);
    }

    private static String splitNamespace(String combined) {
        int colon = combined.indexOf(':');
        return colon < 0 ? "minecraft" : combined.substring(0, colon);
    }

    private static String splitPath(String combined) {
        int colon = combined.indexOf(':');
        return colon < 0 ? combined : combined.substring(colon + 1);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResourceLocation location)) {
            return false;
        }
        return namespace.equals(location.namespace) && path.equals(location.path);
    }

    @Override
    public int hashCode() {
        return 31 * namespace.hashCode() + path.hashCode();
    }

    @Override
    public int compareTo(ResourceLocation other) {
        int byNamespace = namespace.compareTo(other.namespace);
        return byNamespace != 0 ? byNamespace : path.compareTo(other.path);
    }
}
