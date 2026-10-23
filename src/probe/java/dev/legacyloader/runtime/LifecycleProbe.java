package dev.legacyloader.runtime;

import dev.legacyloader.LegacyLoaderConfig;
import dev.legacyloader.api.LifecyclePhase;
import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyBridge;
import dev.legacyloader.bridge.LegacyItemView;
import dev.legacyloader.bridge.LegacyModContext;
import dev.legacyloader.bridge.LegacySide;
import dev.legacyloader.classloader.ClassRules;
import dev.legacyloader.classloader.LegacyClassLoader;
import dev.legacyloader.classloader.ModJarSource;
import dev.legacyloader.classloader.PlatformGateway;
import dev.legacyloader.classloader.ShimIndex;
import dev.legacyloader.classloader.SrgMappings;
import dev.legacyloader.discovery.Diagnostic;
import dev.legacyloader.discovery.DiscoveredMod;
import dev.legacyloader.discovery.OldModScanner;
import dev.legacyloader.profile.Forge1122Profile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Headless lifecycle probe: construct a legacy mod and run preInit against a
 * recording bridge, without starting Minecraft. This is the step between
 * "all classes load" and "the game starts".
 *
 * <pre>gradle runLifecycleProbe --args="/path/to/mod.jar"</pre>
 */
public final class LifecycleProbe {
    private static final List<String> registered = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Path jar = Path.of(args[0]);
        Path temp = java.nio.file.Files.createTempDirectory("legacy-lifecycle-probe");
        LegacyLoaderConfig config = new LegacyLoaderConfig(
                temp, jar.getParent(), true, false, false,
                Set.of("1.12.2"), 8);

        Forge1122Profile profile = new Forge1122Profile();
        OldModScanner.ScanResult scan = new OldModScanner(config, profile).scan();
        if (scan.mods().isEmpty()) {
            throw new IllegalStateException("No mod found in " + config.legacyModsDirectory());
        }
        DiscoveredMod discovered = scan.mods().get(0);
        if (!discovered.isEnabled()) {
            throw new IllegalStateException("Mod disabled: " + discovered.disabledReason());
        }

        ShimIndex shims = ShimIndex.load(profile);
        ClassRules rules = new ClassRules(shims);
        PlatformGateway gateway = new PlatformGateway(LifecycleProbe.class.getClassLoader(), rules);
        SrgMappings mappings = SrgMappings.load(profile.srgMappingsDirectory());
        List<String> diagnostics = new ArrayList<>();

        ModJarSource source = new ModJarSource(discovered.jarPath());
        LegacyClassLoader loader = new LegacyClassLoader(profile, discovered.modId(), source,
                rules, gateway, List.of(), mappings, diagnostics::add);
        LegacySide side = java.util.Arrays.asList(args).contains("--client")
                ? LegacySide.CLIENT : LegacySide.SERVER;
        LegacyModContainer container = new LegacyModContainer(discovered, source, side,
                temp.resolve("config"), temp);
        container.attachClassLoader(loader);

        BridgeHolder.install(new RecordingBridge(container));
        System.out.println("entry: " + discovered.modClass());
        container.construct();
        System.out.println("state after construct: " + container.state());
        if (container.failure() != null) {
            container.failure().printStackTrace(System.out);
            return;
        }

        container.invokePhase(LifecyclePhase.PRE_INIT, profile);
        System.out.println("state after preInit: " + container.state());
        if (container.failure() != null) {
            container.failure().printStackTrace(System.out);
            return;
        }

        int subscribers = registerSubscribers(loader, source);
        System.out.println("event subscribers: " + subscribers);

        net.minecraftforge.registries.LegacyRegistryEvents.fire("minecraft:blocks");
        net.minecraftforge.registries.LegacyRegistryEvents.fire("minecraft:items");
        System.out.println("registered blocks: " + registered.stream()
                .filter(value -> value.startsWith("block:")).count());
        System.out.println("registered items: " + registered.stream()
                .filter(value -> value.startsWith("item:")).count());

        container.invokePhase(LifecyclePhase.INIT, profile);
        System.out.println("state after init: " + container.state());
        if (container.failure() != null) {
            container.failure().printStackTrace(System.out);
            return;
        }

        container.invokePhase(LifecyclePhase.POST_INIT, profile);
        System.out.println("state after postInit: " + container.state());
        if (container.failure() != null) {
            container.failure().printStackTrace(System.out);
        }
        System.out.println("diagnostics: " + diagnostics);
    }

    private static int registerSubscribers(LegacyClassLoader loader, ModJarSource source) {
        String descriptor = "Lnet/minecraftforge/fml/common/Mod$EventBusSubscriber;";
        int count = 0;
        for (String entry : source.classEntryNames()) {
            if (!entry.endsWith(".class")) {
                continue;
            }
            String binaryName = entry.substring(0, entry.length() - ".class".length())
                    .replace('/', '.');
            byte[] bytes = source.readClassBytes(binaryName);
            if (bytes == null) {
                continue;
            }
            java.util.Map<String, Object> annotation;
            try {
                annotation = dev.legacyloader.classloader.BytecodeInspector.readAnnotation(bytes, descriptor);
            } catch (RuntimeException malformed) {
                continue;
            }
            if (annotation == null) {
                continue;
            }
            try {
                Class<?> subscriber = Class.forName(binaryName, true, loader);
                net.minecraftforge.common.MinecraftForge.EVENT_BUS.registerClass(subscriber);
                count++;
            } catch (Throwable t) {
                System.out.println("[subscriber] " + binaryName + " -> " + t);
            }
        }
        return count;
    }

    private record RecordingBridge(LegacyModContext context) implements LegacyBridge {
        @Override
        public void registerBlock(LegacyBlockView block, String namespace, String path) {
            registered.add("block:" + namespace + ":" + path);
        }

        @Override
        public void registerItem(LegacyItemView item, String namespace, String path) {
            registered.add("item:" + namespace + ":" + path);
        }

        @Override
        public void registerEventListener(Object listener) {
        }

        @Override
        public void unregisterEventListener(Object listener) {
        }

        @Override
        public boolean isModLoaded(String modId) {
            return "baubles".equals(modId);
        }

        @Override
        public LegacyModContext currentContext() {
            return context;
        }

        @Override
        public void reportUnsupported(String feature, String detail) {
            System.out.println("[unsupported] " + feature + ": " + detail);
        }

        @Override
        public void log(String level, String message) {
        }

        @Override
        public void reportDegraded(String feature, String detail) {
        }
    }
}
