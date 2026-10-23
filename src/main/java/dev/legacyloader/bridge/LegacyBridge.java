package dev.legacyloader.bridge;

/**
 * The only surface the shim layer may call into the modern game through.
 *
 * <p>Rules for this interface:
 * <ul>
 *   <li>no modern Minecraft, Fabric or NeoForge types in signatures;</li>
 *   <li>no generics that expose modern classes;</li>
 *   <li>methods must be safe to call from legacy code at any time.</li>
 * </ul>
 *
 * The implementation lives in the loader's own classloader and is installed
 * into {@link BridgeHolder} before any legacy class is defined.
 */
public interface LegacyBridge {
    void registerBlock(LegacyBlockView block, String namespace, String path);

    void registerItem(LegacyItemView item, String namespace, String path);

    default void registerSound(LegacySoundView sound, String namespace, String path) {
    }

    void registerEventListener(Object listener);

    void unregisterEventListener(Object listener);

    boolean isModLoaded(String modId);

    LegacyModContext currentContext();

    void reportUnsupported(String feature, String detail);

    void log(String level, String message);

    /** Called when settings such as the creative tab are still pending. */
    void reportDegraded(String feature, String detail);
}
