package dev.legacyloader.classloader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/**
 * Rewrites SRG member names to MCP names before a legacy class is defined.
 *
 * <p>Only names present in the mapping table are touched. mod code's own
 * methods, Forge API names and MCP-named code pass through unchanged.
 */
public final class SrgRemapper extends Remapper {
    private final SrgMappings mappings;

    public SrgRemapper(SrgMappings mappings) {
        this.mappings = mappings;
    }

    public byte[] transform(byte[] classBytes) {
        ClassReader reader = new ClassReader(classBytes);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new ClassRemapper(writer, this), 0);
        return writer.toByteArray();
    }

    @Override
    public String mapMethodName(String owner, String name, String descriptor) {
        return mappings.method(name);
    }

    @Override
    public String mapFieldName(String owner, String name, String descriptor) {
        return mappings.field(name);
    }

    @Override
    public String mapInvokeDynamicMethodName(String name, String descriptor) {
        return mappings.method(name);
    }
}
