package dev.legacyloader.classloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * SRG -> MCP name mapping for production 1.12.2 mod jars.
 *
 * <p>Very important detail: published 1.12.2 jars are reobfuscated to SRG names
 * ({@code func_149711_c}, {@code field_151573_f}), not MCP names. Our shims are
 * written with readable MCP names, so legacy bytecode has to be remapped while
 * it is defined. Class names are already MCP in 1.12 SRG and stay untouched.
 */
public final class SrgMappings {
    private final Map<String, String> methods;
    private final Map<String, String> fields;

    private SrgMappings(Map<String, String> methods, Map<String, String> fields) {
        this.methods = Map.copyOf(methods);
        this.fields = Map.copyOf(fields);
    }

    public static SrgMappings load(String directory) {
        if (directory == null || directory.isBlank()) {
            return null;
        }
        Map<String, String> methods = readCsv(directory + "/methods.csv");
        Map<String, String> fields = readCsv(directory + "/fields.csv");
        return new SrgMappings(methods, fields);
    }

    private static Map<String, String> readCsv(String resource) {
        Map<String, String> mapping = new HashMap<>();
        try (InputStream in = SrgMappings.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Missing mapping resource " + resource);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line = reader.readLine(); // header: searge,name,side,desc
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    int firstComma = line.indexOf(',');
                    if (firstComma <= 0) {
                        continue;
                    }
                    int secondComma = line.indexOf(',', firstComma + 1);
                    String srg = line.substring(0, firstComma);
                    String mcp = secondComma < 0
                            ? line.substring(firstComma + 1)
                            : line.substring(firstComma + 1, secondComma);
                    if (!mcp.isBlank() && !mcp.equals(srg)) {
                        mapping.put(srg, mcp);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read mapping resource " + resource, e);
        }
        return mapping;
    }

    public String method(String srgName) {
        return methods.getOrDefault(srgName, srgName);
    }

    public String field(String srgName) {
        return fields.getOrDefault(srgName, srgName);
    }

    public int methodCount() {
        return methods.size();
    }

    public int fieldCount() {
        return fields.size();
    }
}
