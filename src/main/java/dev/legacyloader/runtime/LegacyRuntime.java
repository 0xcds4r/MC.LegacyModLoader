package dev.legacyloader.runtime;

import dev.legacyloader.LegacyLoaderConfig;
import dev.legacyloader.asset.LegacyAssetPacks;
import dev.legacyloader.asset.LegacyCreativeTab;
import dev.legacyloader.api.LegacyProfile;
import dev.legacyloader.api.LifecyclePhase;
import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacyBridge;
import dev.legacyloader.bridge.LegacySide;
import dev.legacyloader.bridge.impl.FabricBridge;
import dev.legacyloader.classloader.ClassRules;
import dev.legacyloader.classloader.BytecodeInspector;
import dev.legacyloader.classloader.LegacyClassLoader;
import dev.legacyloader.classloader.ModJarSource;
import dev.legacyloader.classloader.PlatformGateway;
import dev.legacyloader.classloader.ShimIndex;
import dev.legacyloader.classloader.SrgMappings;
import dev.legacyloader.discovery.DependencyResolver;
import dev.legacyloader.discovery.Diagnostic;
import dev.legacyloader.discovery.DiscoveredMod;
import dev.legacyloader.discovery.OldModScanner;
import dev.legacyloader.profile.Forge1122Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Orchestrates one boot of the legacy runtime:
 * scan -> resolve -> classloaders -> construct -> lifecycle phases.
 *
 * <p>Phase order matches Forge 1.12.2: all mods are constructed first, then
 * every mod runs preInit, then init, then postInit, then loadComplete. Running
 * the full lifecycle of one mod before touching the next would break mods that
 * expect another mod's preInit to have happened before their init.
 */
public final class LegacyRuntime {
    private static final Logger LOG = LoggerFactory.getLogger("legacyloader");

    public record BootReport(List<LegacyModContainer> containers,
                             List<Diagnostic> diagnostics,
                             List<String> unsupportedFeatures) {
        public boolean hasFailures() {
            return containers.stream().anyMatch(LegacyModContainer::isFailed);
        }
    }

    private final LegacyLoaderConfig config;
    private final LegacyProfile profile;
    private final List<Diagnostic> diagnostics = new ArrayList<>();
    private final List<String> unsupportedFeatures = new ArrayList<>();
    private final List<LegacyModContainer> containers = new ArrayList<>();
    private final Map<String, LegacyModContainer> containersById = new LinkedHashMap<>();
    private final Map<String, ModJarSource> sources = new HashMap<>();

    private volatile LegacyBridge bridge;
    private boolean booted;

    public LegacyRuntime(LegacyLoaderConfig config) {
        this(config, new Forge1122Profile());
    }

    public LegacyRuntime(LegacyLoaderConfig config, LegacyProfile profile) {
        this.config = config;
        this.profile = profile;
    }

    public LegacyProfile profile() {
        return profile;
    }

    public synchronized BootReport boot() {
        if (booted) {
            throw new IllegalStateException("Legacy runtime already booted");
        }
        booted = true;
        config.logSummary();

        OldModScanner.ScanResult scan = new OldModScanner(config, profile).scan();
        diagnostics.addAll(scan.diagnostics());

        DependencyResolver.Resolution resolution = new DependencyResolver()
                .resolve(scan.mods(), profile.providedMods());
        diagnostics.addAll(resolution.diagnostics());

        ShimIndex shimIndex = ShimIndex.load(profile);
        ClassRules rules = new ClassRules(shimIndex);
        PlatformGateway gateway = new PlatformGateway(LegacyRuntime.class.getClassLoader(), rules);
        SrgMappings mappings = SrgMappings.load(profile.srgMappingsDirectory());
        LOG.info("Legacy profile {}: {} shims, {} SRG methods, {} SRG fields, {} discovered mods",
                profile.id(), shimIndex.size(),
                mappings == null ? 0 : mappings.methodCount(),
                mappings == null ? 0 : mappings.fieldCount(),
                scan.mods().size());

        this.bridge = new FabricBridge(this, profile, gateway);
        BridgeHolder.install(bridge);

        for (DiscoveredMod mod : resolution.ordered()) {
            try {
                ModJarSource source = new ModJarSource(mod.jarPath());
                sources.put(mod.modId(), source);
                List<LegacyClassLoader> dependencies = resolveDependencies(mod);
                LegacyModContainer container = new LegacyModContainer(
                        mod, source, currentSide(),
                        config.gameDirectory().resolve("config").resolve("legacyloader").resolve(mod.modId()),
                        config.gameDirectory());
                LegacyClassLoader loader = new LegacyClassLoader(
                        profile, mod.modId(), source, rules, gateway, dependencies, mappings,
                        message -> {
                            diagnostics.add(Diagnostic.warn(message));
                            LOG.debug(message);
                        });
                container.attachClassLoader(loader);
                containers.add(container);
                containersById.put(mod.modId(), container);
            } catch (RuntimeException e) {
                diagnostics.add(Diagnostic.error("Cannot create classloader for " + mod.modId() + ": " + e.getMessage()));
                LOG.error("Cannot create classloader for {}", mod.modId(), e);
            }
        }

        Path assetRoot = LegacyAssetPacks.prepare(config, containers);

        for (LegacyModContainer container : containers) {
            bridgeSetCurrent(container);
            try {
                container.construct();
            } finally {
                bridgeClearCurrent();
            }
        }

        registerEventSubscribers();

        // Forge 1.12.2 fires registry events once all subscribers exist and
        // before any mod's preInit. Fire per mod so the bridge can attribute
        // registrations to the right namespace.
        fireRegistryEvents();

        for (LifecyclePhase phase : List.of(
                LifecyclePhase.PRE_INIT,
                LifecyclePhase.INIT,
                LifecyclePhase.POST_INIT,
                LifecyclePhase.LOAD_COMPLETE)) {
            for (LegacyModContainer container : containers) {
                bridgeSetCurrent(container);
                try {
                    container.invokePhase(phase, profile);
                } finally {
                    bridgeClearCurrent();
                }
            }
        }

        logDiagnostics(resolution);
        if (bridge instanceof FabricBridge fabricBridge) {
            LegacyAssetPacks.writeItemModels(assetRoot, fabricBridge);
            LegacyCreativeTab.register(fabricBridge);
        }
        cleanup();
        return new BootReport(List.copyOf(containers), List.copyOf(diagnostics), List.copyOf(unsupportedFeatures));
    }

    private LegacySide currentSide() {
        return config.clientEnvironment() ? LegacySide.CLIENT : LegacySide.SERVER;
    }

    /**
     * Forge automatically registers classes annotated with
     * {@code @Mod.EventBusSubscriber}. Reproduce that with an ASM scan so no
     * class is initialized unless it actually carries the annotation.
     */
    private void registerEventSubscribers() {
        String descriptor = "Lnet/minecraftforge/fml/common/Mod$EventBusSubscriber;";
        String sideName = currentSide().name();
        for (LegacyModContainer container : containers) {
            ModJarSource source = sources.get(container.modId());
            if (source == null || container.classLoader() == null) {
                continue;
            }
            for (String entry : source.classEntryNames()) {
                if (!entry.endsWith(".class") || entry.startsWith("META-INF/")) {
                    continue;
                }
                String binaryName = entry.substring(0, entry.length() - ".class".length())
                        .replace('/', '.');
                byte[] bytes = source.readClassBytes(binaryName);
                if (bytes == null) {
                    continue;
                }
                Map<String, Object> annotation;
                try {
                    annotation = BytecodeInspector.readAnnotation(bytes, descriptor);
                } catch (RuntimeException malformed) {
                    continue;
                }
                if (annotation == null) {
                    continue;
                }
                Object sideValue = annotation.get("value");
                if (sideValue != null && !String.valueOf(sideValue).contains(sideName)) {
                    continue;
                }
                bridgeSetCurrent(container);
                try {
                    Class<?> subscriber = Class.forName(binaryName, true, container.classLoader());
                    Object bus = eventBus(container);
                    bus.getClass().getMethod("registerClass", Class.class).invoke(bus, subscriber);
                } catch (Throwable t) {
                    diagnostics.add(Diagnostic.warn("Cannot register event subscriber "
                            + binaryName + " for " + container.modId() + ": " + t));
                } finally {
                    bridgeClearCurrent();
                }
            }
        }
    }

    private Object eventBus(LegacyModContainer container) throws ReflectiveOperationException {
        Class<?> forge = Class.forName("net.minecraftforge.common.MinecraftForge",
                true, container.classLoader());
        return forge.getField("EVENT_BUS").get(null);
    }

    private void fireRegistryEvents() {
        if (containers.isEmpty()) {
            return;
        }
        try {
            // Shims live in ShimClassLoader, not on Knot's classpath.
            ClassLoader shimLoader = containers.get(0).classLoader();
            Class<?> helper = Class.forName("net.minecraftforge.registries.LegacyRegistryEvents",
                    true, shimLoader);
            java.lang.reflect.Method fire = helper.getMethod("fire", String.class);
            for (String registry : List.of("minecraft:blocks", "minecraft:items")) {
                for (LegacyModContainer container : containers) {
                    if (container.classLoader() == null || container.isFailed()) {
                        continue;
                    }
                    bridgeSetCurrent(container);
                    try {
                        fire.invoke(null, registry);
                    } catch (Throwable t) {
                        diagnostics.add(Diagnostic.warn("Registry event " + registry + " failed for "
                                + container.modId() + ": " + t));
                    } finally {
                        bridgeClearCurrent();
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
            diagnostics.add(Diagnostic.error("Registry event bridge is unavailable: " + e));
        }
    }

    private List<LegacyClassLoader> resolveDependencies(DiscoveredMod mod) {
        List<LegacyClassLoader> result = new ArrayList<>();
        Set<String> ids = new HashSet<>(mod.dependencies().required());
        ids.addAll(mod.dependencies().after());
        for (String id : ids) {
            LegacyModContainer dependency = containersById.get(id);
            if (dependency != null && dependency.classLoader() != null) {
                result.add(dependency.classLoader());
            }
        }
        return result;
    }

    private void bridgeSetCurrent(LegacyModContainer container) {
        if (bridge instanceof FabricBridge fabricBridge) {
            fabricBridge.pushContext(container);
        }
    }

    private void bridgeClearCurrent() {
        if (bridge instanceof FabricBridge fabricBridge) {
            fabricBridge.popContext();
        }
    }

    private void logDiagnostics(DependencyResolver.Resolution resolution) {
        for (Diagnostic diagnostic : diagnostics) {
            switch (diagnostic.severity()) {
                case INFO -> LOG.info(diagnostic.message());
                case WARN -> LOG.warn(diagnostic.message());
                case ERROR -> LOG.error(diagnostic.message());
            }
        }
        for (DiscoveredMod disabled : resolution.disabled()) {
            LOG.warn("Disabled {}: {}", disabled.modId(), disabled.disabledReason());
        }
        long active = containers.stream().filter(c -> !c.isFailed()).count();
        LOG.info("Legacy runtime ready: {}/{} mods active", active, containers.size());
    }

    private void cleanup() {
        // Sources stay open: legacy mods may read their own jar during runtime,
        // and closing a JarFile under a live classloader is not worth the risk.
    }

    public void shutdown() {
        for (ModJarSource source : sources.values()) {
            try {
                source.close();
            } catch (IOException ignored) {
                // best effort
            }
        }
        sources.clear();
        containers.clear();
        containersById.clear();
        BridgeHolder.uninstall();
        bridge = null;
    }

    public List<LegacyModContainer> containers() {
        return List.copyOf(containers);
    }

    public boolean isModLoaded(String modId) {
        if (profile.providedMods().contains(modId)) {
            return true;
        }
        LegacyModContainer container = containersById.get(modId);
        return container != null && !container.isFailed();
    }

    public Set<String> loadedModIds() {
        return Set.copyOf(containersById.keySet());
    }

    public void reportUnsupported(String feature, String detail) {
        String message = feature + ": " + detail;
        if (!unsupportedFeatures.contains(message)) {
            unsupportedFeatures.add(message);
            LOG.warn("[unsupported] {}", message);
        }
    }

    public List<String> unsupportedFeatures() {
        return List.copyOf(unsupportedFeatures);
    }
}
