package dev.legacyloader.classloader;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Static inspection of legacy bytecode. Used before a class is loaded so the
 * loader can find {@code @Mod} without triggering static initializers.
 */
public final class BytecodeInspector {
    private BytecodeInspector() {
    }

    /**
     * @return annotation values (strings, booleans, arrays flattened to strings)
     * or {@code null} when the class carries no such annotation.
     */
    public static Map<String, Object> readAnnotation(byte[] classBytes, String annotationDescriptor) {
        Map<String, Object> values = new LinkedHashMap<>();
        boolean[] found = {false};

        ClassReader reader = new ClassReader(classBytes);
        reader.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                if (!annotationDescriptor.equals(descriptor)) {
                    return null;
                }
                found[0] = true;
                return new AnnotationVisitor(Opcodes.ASM9) {
                    @Override
                    public void visit(String name, Object value) {
                        values.put(name, value);
                    }

                    @Override
                    public void visitEnum(String name, String descriptor, String value) {
                        values.put(name, value);
                    }

                    @Override
                    public AnnotationVisitor visitArray(String name) {
                        return new AnnotationVisitor(Opcodes.ASM9) {
                            @Override
                            public void visit(String ignored, Object value) {
                                values.merge(name, String.valueOf(value), (a, b) -> a + "\u0000" + b);
                            }
                        };
                    }
                };
            }
        }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        return found[0] ? values : null;
    }

    public static String toDescriptor(String binaryName) {
        return "L" + binaryName.replace('.', '/') + ";";
    }
}
