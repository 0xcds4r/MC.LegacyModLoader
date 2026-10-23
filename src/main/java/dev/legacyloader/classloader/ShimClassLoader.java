package dev.legacyloader.classloader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Defines the 1.12 shim classes in their own namespace-isolated loader.
 *
 * <p>This is required because several 1.12 names still exist in modern
 * Minecraft with a different API (for example
 * {@code net.minecraft.client.particle.Particle} or
 * {@code net.minecraft.client.Minecraft}). Delegating shim lookup to Knot would
 * return the modern class, run modern mixins on it, and corrupt client
 * rendering. Shims must therefore be defined here, from the loader's own jar,
 * never by the application classloader.
 *
 * <p>Only shim names are defined here. Bridge interfaces and approved shared
 * libraries are delegated to the application loader; JDK classes go to the
 * platform loader.
 */
public final class ShimClassLoader extends ClassLoader implements Closeable {
    static {
        registerAsParallelCapable();
    }

    private final ClassLoader applicationLoader;
    private final ClassLoader platformLoader = ClassLoader.getPlatformClassLoader();
    private final ClassRules rules;
    private final List<Path> roots = new ArrayList<>();
    private final List<JarFile> jars = new ArrayList<>();
    private final java.util.Map<String, byte[]> embedded = new java.util.HashMap<>();
    private final ProtectionDomain protectionDomain;

    public ShimClassLoader(ClassLoader applicationLoader, ClassRules rules) {
        super(null);
        this.applicationLoader = applicationLoader;
        this.rules = rules;
        discoverRoots();
        this.protectionDomain = buildProtectionDomain();
    }

    private void discoverRoots() {
        try {
            CodeSource source = ShimClassLoader.class.getProtectionDomain().getCodeSource();
            if (source == null) {
                throw new IllegalStateException("Shim code source is unavailable");
            }
            Path location = Path.of(source.getLocation().toURI()).toAbsolutePath().normalize();
            if (Files.isDirectory(location)) {
                roots.add(location);
                Path sibling = location.getParent().resolve("shim1122");
                if (Files.isDirectory(sibling)) {
                    roots.add(sibling);
                }
            } else {
                roots.add(location);
                JarFile modJar = new JarFile(location.toFile(), false);
                jars.add(modJar);
                JarEntry nested = modJar.getJarEntry("META-INF/legacyloader/shims-1.12.2.jar");
                if (nested == null) {
                    throw new IllegalStateException("Embedded shim jar is missing from " + location);
                }
                try (InputStream in = modJar.getInputStream(nested);
                     java.util.jar.JarInputStream shims = new java.util.jar.JarInputStream(in)) {
                    java.util.jar.JarEntry entry;
                    while ((entry = shims.getNextJarEntry()) != null) {
                        if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                            embedded.put(entry.getName(), shims.readAllBytes());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Cannot locate shim classes", e);
        }
    }

    private ProtectionDomain buildProtectionDomain() {
        try {
            URL location = roots.get(0).toUri().toURL();
            return new ProtectionDomain(new CodeSource(location, (Certificate[]) null), null, this, null);
        } catch (Exception e) {
            return new ProtectionDomain(null, null);
        }
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

            ClassRules.Visibility visibility = rules.classify(name);
            Class<?> result;
            if (visibility == ClassRules.Visibility.SHIM) {
                result = findShimClass(name);
                if (result == null) {
                    throw new ClassNotFoundException("Shim not found in loader jar: " + name);
                }
            } else if (name.startsWith("dev.legacyloader.bridge.")
                    || visibility == ClassRules.Visibility.SHARED) {
                result = Class.forName(name, false, applicationLoader);
            } else if (visibility == ClassRules.Visibility.PLATFORM) {
                result = Class.forName(name, false, platformLoader);
            } else {
                throw new ClassNotFoundException("Not visible to shims: " + name);
            }

            if (resolve) {
                resolveClass(result);
            }
            return result;
        }
    }

    private Class<?> findShimClass(String name) {
        byte[] bytes = readClassBytes(name);
        if (bytes == null) {
            return null;
        }
        return defineClass(name, bytes, 0, bytes.length, protectionDomain);
    }

    private byte[] readClassBytes(String name) {
        String entry = name.replace('.', '/') + ".class";
        byte[] embeddedBytes = embedded.get(entry);
        if (embeddedBytes != null) {
            return embeddedBytes;
        }
        for (JarFile jar : jars) {
            JarEntry candidate = jar.getJarEntry(entry);
            if (candidate != null) {
                try (InputStream in = jar.getInputStream(candidate)) {
                    return in.readAllBytes();
                } catch (IOException ignored) {
                    return null;
                }
            }
        }
        for (Path root : roots) {
            Path file = root.resolve(entry);
            if (Files.isRegularFile(file)) {
                try {
                    return Files.readAllBytes(file);
                } catch (IOException ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public URL getResource(String name) {
        for (JarFile jar : jars) {
            JarEntry entry = jar.getJarEntry(name);
            if (entry != null) {
                try {
                    return new URL("jar:" + jar.getName() + "!/" + name);
                } catch (Exception ignored) {
                    return null;
                }
            }
        }
        for (Path root : roots) {
            Path file = root.resolve(name);
            if (Files.isRegularFile(file)) {
                try {
                    return file.toUri().toURL();
                } catch (Exception ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public void close() {
        for (JarFile jar : jars) {
            try {
                jar.close();
            } catch (IOException ignored) {
                // best effort
            }
        }
        jars.clear();
    }
}
