package dev.legacyloader.classloader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Read-only view of one original mod jar. The file is opened read-only once and
 * never modified. The protection domain points at the original file so mods
 * that inspect their own code source keep working.
 */
public final class ModJarSource implements Closeable {
    private final Path path;
    private final JarFile jar;
    private final ProtectionDomain protectionDomain;

    public ModJarSource(Path path) {
        this.path = path.toAbsolutePath().normalize();
        try {
            // verify=false: legacy jars are often signed with expired or
            // untrusted certificates; we never trust their signatures anyway.
            this.jar = new JarFile(this.path.toFile(), false);
            URL location = this.path.toUri().toURL();
            CodeSource codeSource = new CodeSource(location, (Certificate[]) null);
            this.protectionDomain = new ProtectionDomain(codeSource, null, null, null);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot open legacy mod jar " + this.path, e);
        }
    }

    public Path path() {
        return path;
    }

    public JarFile jarFile() {
        return jar;
    }

    public ProtectionDomain protectionDomain() {
        return protectionDomain;
    }

    public byte[] readClassBytes(String binaryName) {
        String entryName = binaryName.replace('.', '/') + ".class";
        JarEntry entry = jar.getJarEntry(entryName);
        if (entry == null) {
            return null;
        }
        try (InputStream in = jar.getInputStream(entry)) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + entryName + " from " + path, e);
        }
    }

    public URL findResource(String name) {
        JarEntry entry = jar.getJarEntry(name);
        if (entry == null) {
            return null;
        }
        try {
            return new URL("jar:" + path.toUri() + "!/" + name);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public List<String> classEntryNames() {
        List<String> names = new ArrayList<>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory() && name.endsWith(".class") && !name.startsWith("META-INF/")) {
                names.add(name);
            }
        }
        Collections.sort(names);
        return names;
    }

    @Override
    public void close() throws IOException {
        jar.close();
    }

    @Override
    public String toString() {
        return "ModJarSource[" + path.getFileName() + "]";
    }
}
