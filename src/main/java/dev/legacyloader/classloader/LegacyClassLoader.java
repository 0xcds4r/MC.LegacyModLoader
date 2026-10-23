package dev.legacyloader.classloader;

import dev.legacyloader.api.LegacyProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * One classloader per legacy mod.
 *
 * <h2>Resolution order</h2>
 * <ol>
 *   <li>already loaded by this loader;</li>
 *   <li>registered shims (parent-first, so a mod cannot replace the API);</li>
 *   <li>JDK classes (platform-first);</li>
 *   <li>the mod's own jar (child-first, so shaded libraries win over shared ones);</li>
 *   <li>explicit dependencies' exported packages;</li>
 *   <li>shared third-party libraries approved by {@link ClassRules}.</li>
 * </ol>
 *
 * <p>Modern game classes such as {@code net.minecraft.world.level.block.Block}
 * are not on this list. They match {@link ClassRules.Visibility#BLOCKED} and
 * are rejected by name before any lookup happens.
 *
 * <p>The loader doesn't use a normal parent chain. A single parent cannot model
 * a dependency graph, and {@code super.loadClass} would immediately expose the
 * whole modern classpath. Cross-mod classes are resolved explicitly through
 * {@link #dependencies}.
 *
 * <p>This is isolation, not a security sandbox. Java 25 removed the security
 * manager; a hostile mod can still escape through threads, reflection and
 * {@code ClassLoader.getSystemClassLoader()}. Legacy mods are trusted code that
 * happens to be old.
 */
public final class LegacyClassLoader extends ClassLoader {
    static {
        registerAsParallelCapable();
    }

    private final LegacyProfile profile;
    private final String modId;
    private final ModJarSource source;
    private final ClassRules rules;
    private final PlatformGateway gateway;
    private final List<LegacyClassLoader> dependencies;
    private final Consumer<String> diagnostics;
    private final SrgRemapper remapper;

    public LegacyClassLoader(LegacyProfile profile,
                             String modId,
                             ModJarSource source,
                             ClassRules rules,
                             PlatformGateway gateway,
                             List<LegacyClassLoader> dependencies,
                             SrgMappings mappings,
                             Consumer<String> diagnostics) {
        // A null parent means the bootstrap loader. Everything else goes
        // through PlatformGateway, never through inheritance.
        super(null);
        this.profile = profile;
        this.modId = modId;
        this.source = source;
        this.rules = rules;
        this.gateway = gateway;
        this.dependencies = List.copyOf(dependencies);
        this.remapper = mappings == null ? null : new SrgRemapper(mappings);
        this.diagnostics = diagnostics;
    }

    public String modId() {
        return modId;
    }

    public LegacyProfile profile() {
        return profile;
    }

    public ModJarSource source() {
        return source;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> loaded = findLoadedClass(name);
            if (loaded != null) {
                if (resolve) {
                    resolveClass(loaded);
                }
                return loaded;
            }

            Class<?> result = switch (rules.classify(name)) {
                case SHIM -> gateway.loadShim(name);
                case BLOCKED -> throw blocked(name);
                case PLATFORM -> loadPlatformChecked(name);
                case SHARED -> gateway.loadShared(name);
                case MOD -> loadModClass(name);
            };

            if (result == null) {
                throw new ClassNotFoundException(name + " (not found in " + modId + " or its dependencies)");
            }
            if (resolve) {
                resolveClass(result);
            }
            return result;
        }
    }

    private Class<?> loadPlatformChecked(String name) throws ClassNotFoundException {
        Class<?> result = gateway.loadPlatform(name);
        if (result == null) {
            throw new ClassNotFoundException(name + " (JDK/platform class not available)");
        }
        return result;
    }

    private Class<?> loadModClass(String name) {
        Class<?> own = findOwnClass(name);
        if (own != null) {
            return own;
        }
        for (LegacyClassLoader dependency : dependencies) {
            if (!dependency.isExported(name)) {
                continue;
            }
            try {
                return dependency.loadClass(name);
            } catch (ClassNotFoundException ignored) {
                // try the next dependency
            }
        }
        return null;
    }

    private Class<?> findOwnClass(String name) {
        byte[] bytes = source.readClassBytes(name);
        if (bytes == null) {
            return null;
        }
        if (remapper != null) {
            try {
                bytes = remapper.transform(bytes);
            } catch (RuntimeException e) {
                diagnostics.accept("Failed to remap " + name + " from " + modId + ": " + e);
                throw e;
            }
        }
        definePackageFor(name);
        try {
            return defineClass(name, bytes, 0, bytes.length, source.protectionDomain());
        } catch (LinkageError e) {
            diagnostics.accept("Failed to define " + name + " from " + modId + ": " + e);
            throw e;
        }
    }

    private void definePackageFor(String binaryName) {
        int lastDot = binaryName.lastIndexOf('.');
        if (lastDot <= 0) {
            return;
        }
        String packageName = binaryName.substring(0, lastDot);
        if (getDefinedPackage(packageName) != null) {
            return;
        }
        try {
            definePackage(packageName, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException ignored) {
            // raced with another thread defining the same package
        }
    }

    /**
     * Only packages explicitly exported to dependents are resolved from another
     * mod's loader. A mod cannot reach into an unrelated mod just because it
     * happens to be loaded in the same JVM.
     */
    public boolean isExported(String binaryName) {
        if (binaryName.startsWith("net.minecraft.") || binaryName.startsWith("net.minecraftforge.")) {
            return false;
        }
        String packageName = packageOf(binaryName);
        return !packageName.isEmpty()
                && !packageName.contains(".mixin")
                && !packageName.contains(".asm");
    }

    private static String packageOf(String binaryName) {
        int lastDot = binaryName.lastIndexOf('.');
        return lastDot < 0 ? "" : binaryName.substring(0, lastDot);
    }

    private ClassNotFoundException blocked(String name) {
        String hint = name.startsWith("net.minecraft.")
                ? " Modern Minecraft is hidden from legacy mods; add a shim in shim1122 or a bridge adapter."
                : " Modern loader classes are hidden from legacy mods.";
        diagnostics.accept("Blocked class access from " + modId + ": " + name);
        return new ClassNotFoundException(name + " is blocked by the legacy classloader." + hint);
    }

    @Override
    public URL getResource(String name) {
        // Never expose modern game resources (assets, data, mappings) to legacy code.
        URL own = source.findResource(name);
        if (own != null) {
            return own;
        }
        for (LegacyClassLoader dependency : dependencies) {
            URL found = dependency.getResource(name);
            if (found != null) {
                return found;
            }
        }
        if (isSharedResource(name)) {
            return gateway.findSharedResource(name);
        }
        return null;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Set<URL> urls = new LinkedHashSet<>();
        URL own = source.findResource(name);
        if (own != null) {
            urls.add(own);
        }
        for (LegacyClassLoader dependency : dependencies) {
            urls.addAll(Collections.list(dependency.getResources(name)));
        }
        if (isSharedResource(name)) {
            URL shared = gateway.findSharedResource(name);
            if (shared != null) {
                urls.add(shared);
            }
        }
        return Collections.enumeration(urls);
    }

    private static boolean isSharedResource(String name) {
        return name.startsWith("org/apache/logging/")
                || name.startsWith("org/slf4j/")
                || name.startsWith("com/google/gson/")
                || name.startsWith("com/google/common/");
    }

    @Override
    public String toString() {
        return "LegacyClassLoader[" + modId + " -> " + source.path().getFileName() + "]";
    }

    /** Collected for diagnostics; the loader never closes shared sources. */
    public List<String> classEntries() {
        return new ArrayList<>(source.classEntryNames());
    }
}
