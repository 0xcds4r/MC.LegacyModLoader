package dev.legacyloader.probe;

import dev.legacyloader.bridge.BridgeHolder;
import dev.legacyloader.bridge.LegacyBlockView;
import dev.legacyloader.bridge.LegacyBridge;
import dev.legacyloader.bridge.LegacyItemView;
import dev.legacyloader.bridge.LegacyModContext;
import dev.legacyloader.bridge.LegacySide;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.LegacyRegistryEvents;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Headless self-test for the event bus and registry-event bridge. No Minecraft
 * bootstrap: it proves that a static {@code @Mod.EventBusSubscriber} handler is
 * invoked, receives a registry facade, and that registrations reach the bridge.
 *
 * <pre>gradle runRegistryProbe</pre>
 */
public final class RegistryEventProbe {
    private static final List<String> registeredBlocks = new ArrayList<>();
    private static int handlerCalls;

    public static void main(String[] args) {
        BridgeHolder.install(new FakeBridge());
        MinecraftForge.EVENT_BUS.registerClass(Subscriber.class);

        LegacyRegistryEvents.fire("minecraft:blocks");

        if (handlerCalls != 1) {
            throw new AssertionError("Expected one handler call, got " + handlerCalls);
        }
        if (!registeredBlocks.equals(List.of("testmod:probe_block"))) {
            throw new AssertionError("Unexpected registered blocks: " + registeredBlocks);
        }
        System.out.println("registry/event self-test OK: " + registeredBlocks);
        BridgeHolder.uninstall();
    }

    @Mod.EventBusSubscriber(modid = "testmod")
    public static final class Subscriber {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
            handlerCalls++;
            Block block = new Block(Material.ROCK)
                    .setRegistryName(new ResourceLocation("testmod", "probe_block"));
            event.getRegistry().register(block);
        }
    }

    private static final class FakeBridge implements LegacyBridge {
        @Override
        public void registerBlock(LegacyBlockView block, String namespace, String path) {
            registeredBlocks.add(namespace + ":" + path);
        }

        @Override
        public void registerItem(LegacyItemView item, String namespace, String path) {
        }

        @Override
        public void registerEventListener(Object listener) {
        }

        @Override
        public void unregisterEventListener(Object listener) {
        }

        @Override
        public boolean isModLoaded(String modId) {
            return true;
        }

        @Override
        public LegacyModContext currentContext() {
            return FakeContext.INSTANCE;
        }

        @Override
        public void reportUnsupported(String feature, String detail) {
        }

        @Override
        public void log(String level, String message) {
        }

        @Override
        public void reportDegraded(String feature, String detail) {
        }
    }

    private enum FakeContext implements LegacyModContext {
        INSTANCE;

        @Override
        public String modId() {
            return "testmod";
        }

        @Override
        public String modName() {
            return "Test Mod";
        }

        @Override
        public String modVersion() {
            return "1.0";
        }

        @Override
        public LegacySide side() {
            return LegacySide.CLIENT;
        }

        @Override
        public Path configDirectory() {
            return Path.of(".");
        }

        @Override
        public Path gameDirectory() {
            return Path.of(".");
        }

        @Override
        public String loggerName() {
            return "testmod";
        }

        @Override
        public Object modInstance() {
            return null;
        }

        @Override
        public ClassLoader modClassLoader() {
            return RegistryEventProbe.class.getClassLoader();
        }

        @Override
        public Path sourceJar() {
            return Path.of(".");
        }
    }
}
