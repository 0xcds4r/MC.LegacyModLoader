package dev.legacyloader;

import dev.legacyloader.runtime.LegacyRuntime;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fabric entry point. The loader is intentionally fail-soft: one broken legacy
 * mod must not prevent the modern game from starting.
 */
public final class LegacyModLoader implements ModInitializer {
    public static final String MOD_ID = "legacyloader";
    private static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    private static LegacyRuntime runtime;

    @Override
    public void onInitialize() {
        LegacyLoaderConfig config = LegacyLoaderConfig.defaults();
        LegacyRuntime created = new LegacyRuntime(config);
        try {
            LegacyRuntime.BootReport report = created.boot();
            runtime = created;
            LOG.info("Legacy Mod Loader booted {} mods ({} failures, {} unsupported features)",
                    report.containers().size(),
                    report.containers().stream().filter(c -> c.failure() != null).count(),
                    report.unsupportedFeatures().size());
            if (report.hasFailures()) {
                report.containers().stream()
                        .filter(c -> c.failure() != null)
                        .forEach(c -> LOG.error("Legacy mod {} failed", c.modId(), c.failure()));
            }
        } catch (Throwable t) {
            LOG.error("Legacy Mod Loader failed to boot; modern game continues without legacy mods", t);
            created.shutdown();
        }
    }

    public static LegacyRuntime runtime() {
        return runtime;
    }
}
