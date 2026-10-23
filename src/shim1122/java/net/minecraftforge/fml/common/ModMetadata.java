package net.minecraftforge.fml.common;

import java.util.List;

/**
 * Metadata object passed to lifecycle events.
 */
public class ModMetadata {
    public String modId = "";
    public String name = "";
    public String version = "";
    public String description = "";
    public String url = "";
    public String updateUrl = "";
    public String parent = "";
    public List<String> authorList = List.of();
    public List<String> credits = List.of();
    public List<String> dependencies = List.of();
}
