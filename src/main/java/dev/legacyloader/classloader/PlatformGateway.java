package dev.legacyloader.classloader;

import java.net.URL;

/**
 * Controlled access to the "outside". The legacy loader never calls
 * {@code super.loadClass}; everything that comes from the platform goes through
 * this gateway, which only exposes JDK packages, shim classes and the small set
 * of shared libraries approved by {@link ClassRules}.
 */
public final class PlatformGateway {
    private final ClassLoader applicationLoader;
    private final ClassLoader platformLoader;
    private final ClassRules rules;
    private final ShimClassLoader shims;

    public PlatformGateway(ClassLoader applicationLoader, ClassRules rules) {
        this.applicationLoader = applicationLoader;
        this.platformLoader = ClassLoader.getPlatformClassLoader();
        this.rules = rules;
        this.shims = new ShimClassLoader(applicationLoader, rules);
    }

    /**
     * @return the class, or {@code null} when the platform does not have it
     * (for example {@code javax.annotation.Nullable} on a modern JDK).
     */
    public Class<?> loadPlatform(String binaryName) {
        try {
            return Class.forName(binaryName, false, platformLoader);
        } catch (ClassNotFoundException e) {
            // Some javax.* annotations live next to the game, not in the JDK.
            try {
                return Class.forName(binaryName, false, applicationLoader);
            } catch (ClassNotFoundException ignored) {
                return null;
            }
        }
    }

    /**
     * Loads a class from the shim layer. Shim classes are ordinary mod classes
     * from the loader's point of view, so they may reference modern Minecraft
     * APIs, but legacy code never obtains those references directly.
     */
    public Class<?> loadShim(String binaryName) throws ClassNotFoundException {
        if (!rules.isShim(binaryName)) {
            throw new ClassNotFoundException(binaryName + " is not a registered shim");
        }
        return shims.loadClass(binaryName);
    }

    public Class<?> loadShared(String binaryName) throws ClassNotFoundException {
        if (rules.classify(binaryName) != ClassRules.Visibility.SHARED) {
            throw new ClassNotFoundException(binaryName + " is not a shared library");
        }
        return Class.forName(binaryName, false, applicationLoader);
    }

    public URL findSharedResource(String name) {
        return applicationLoader.getResource(name);
    }

    public ClassLoader applicationLoader() {
        return applicationLoader;
    }
}
