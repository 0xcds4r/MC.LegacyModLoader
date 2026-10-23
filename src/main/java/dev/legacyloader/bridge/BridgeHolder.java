package dev.legacyloader.bridge;

/**
 * Static hand-off point between the isolated shim layer and the loader.
 * Shims must not capture the bridge in static fields before it is installed;
 * always call {@link #require()} at use time.
 */
public final class BridgeHolder {
    private static volatile LegacyBridge bridge;

    private BridgeHolder() {
    }

    public static void install(LegacyBridge implementation) {
        if (bridge != null) {
            throw new IllegalStateException("Legacy bridge already installed");
        }
        bridge = implementation;
    }

    public static void uninstall() {
        bridge = null;
    }

    public static LegacyBridge require() {
        LegacyBridge current = bridge;
        if (current == null) {
            throw new IllegalStateException("Legacy bridge is not installed; shim used outside a legacy mod phase");
        }
        return current;
    }

    public static LegacyModContext context() {
        return require().currentContext();
    }
}
