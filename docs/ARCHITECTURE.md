# Architecture

## Goals and non-goals

Goals:

- run original 1.12.2 Forge jars without modifying them;
- per-mod class isolation, child-first resolution;
- a versioned shim layer that can grow to 1.7.10 / 1.16.5 profiles;
- deterministic discovery and dependency ordering;
- fail soft: a broken legacy mod must not stop the modern game.

Non-goals for now:

- sandboxing hostile code (Java 25 has no security manager);
- mixin/CoreMod support;
- binary compatibility with 1.12.2 rendering internals;
- perfect behaviour parity. The loader is a compatibility layer, not an
  emulator: unimplemented hooks are reported instead of silently ignored.

## Module boundaries

```
+--------------------------------------------------------------+
| Fabric / modern Minecraft classloader (Knot)                  |
|                                                              |
|  LegacyModLoader -> LegacyRuntime                            |
|  FabricBridge (registration, context, unsupported reports)   |
|  Shims (net.minecraft.*, net.minecraftforge.*, baubles.*)    |
+-----------------------------+--------------------------------+
                              | PlatformGateway allowlist
+-----------------------------v--------------------------------+
| LegacyClassLoader (one per mod)                               |
|  child-first own jar -> dependencies -> shared libs           |
|  SRG -> MCP bytecode remapping before defineClass             |
+--------------------------------------------------------------+
```

The modern side and the legacy side share only:

- JDK classes (`java.*`, `javax.*`);
- the shim classes listed in the generated shim index;
- a narrow bridge (`dev.legacyloader.bridge.*`);
- an explicit allowlist of third-party libraries (log4j, slf4j, gson, guava,
  fastutil, netty, `sun.misc`).

Everything else in `net.minecraft.*`, `net.minecraftforge.*`, `net.neoforged.*`,
`net.fabricmc.*`, `com.mojang.*`, `org.lwjgl.*` is blocked by name before any
lookup happens. Modern Minecraft classes are therefore not reachable through
the legacy loader, not even by `Class.forName`.

## Classloading

### Why not a normal parent chain

A single parent cannot express a dependency graph, and `super.loadClass` would
immediately expose the whole modern classpath. `LegacyClassLoader` has a null
parent and implements resolution itself:

1. class already defined by this loader;
2. registered shim (parent-first, so a mod cannot replace the API);
3. JDK class (platform-first);
4. the mod's own jar (child-first, so shaded libraries win);
5. exported packages of declared dependencies;
6. shared third-party libraries;
7. otherwise `ClassNotFoundException` with an explanation.

Cross-mod access is explicit: only jars listed in `required` / `after` are in
the dependency list, and only their non-mixin packages are exported.

### SRG remapping

Published 1.12.2 jars are reobfuscated to SRG names: `func_149711_c` instead of
`setHardness`, `field_151573_f` instead of `IRON`. Shims are written with
readable MCP names, so every legacy class is passed through an ASM
`ClassRemapper` before `defineClass`. Class names are already MCP in 1.12 SRG
and stay untouched. The mapping tables are MCP stable 39 (1.12) and live in
`META-INF/legacyloader/mappings/1.12.2/`.

Without this step even the simplest production mod fails with
`NoSuchMethodError` on the first Minecraft call.

### Shim index

The shim source set is compiled separately from the loader. A Gradle task writes
every shim binary name to `META-INF/legacyloader/shim-index-1.12.2.txt`. The
runtime uses that index to distinguish "a shim we deliberately provide" from
"a modern class that must stay hidden". The index is not derived from what the
platform classloader can resolve; doing that would defeat isolation.

### Shims are composition, not inheritance

A shim `net.minecraft.block.Block` does **not** extend the modern
`net.minecraft.world.level.block.Block`:

- the 1.12 override surface (`onBlockActivated`, `getStateForPlacement`, ...)
  has incompatible descriptors;
- extending a modern type would leak the modern hierarchy into legacy code.

Instead the shim is a plain data object implementing a bridge view
(`LegacyBlockView`). Registration creates a modern `LegacyBlockAdapter` that
holds the legacy object. Modern calls can later be forwarded to legacy hooks
without either side sharing a type hierarchy.

This also means shims are free to use the 1.12 package names: in 26.3 those
names are unused (`net.minecraft.world.level.block.Block` is the modern one,
`net.minecraft.resources.Identifier` replaced `ResourceLocation`).

### Thread context classloader

During construction and every lifecycle call the thread context classloader is
set to the mod's loader and restored afterwards. Libraries that use
`Thread.currentThread().getContextClassLoader()` then behave as they did on
Forge.

### Resource loading

`getResource` is child-first (own jar, then dependencies, then shared libs).
Modern game resources are never exposed. `getResources` merges the same sources
so `ServiceLoader`-style lookups still see legacy entries.

## Shim layer

Layout:

```
src/shim1122/java/
  net/minecraft/...          game API, 1.12.2 binary names
  net/minecraftforge/...     Forge API, 1.12.2 binary names
  baubles/...                optional API-only compatibility package
  javax/annotation/...       annotations missing from modern JDKs
```

Rules:

1. Shims may call the loader bridge, never modern Minecraft directly.
2. The bridge exposes no modern types in signatures.
3. Every shim method must exist with the 1.12.2 descriptor. Missing methods are
   `NoSuchMethodError` at runtime; missing classes are `NoClassDefFoundError`.
4. Behaviour is added incrementally. A shim that only exists to resolve a method
   descriptor is still useful: it lets the rest of the mod load.

The bridge is installed into `BridgeHolder` before any legacy class is defined.
The current context is a thread-local stack, so static initializers and
lifecycle handlers always see the right mod.

## Lifecycle

Forge 1.12.2 order is reproduced across all mods:

1. construct every mod (static initializers run here);
2. inject `@Mod.Instance`, `@SidedProxy`, `@Mod.Metadata`;
3. scan and register `@Mod.EventBusSubscriber` classes;
4. fire `RegistryEvent.Register<Block>` and `<Item>` per mod, under that mod's
   context, so registrations get the right namespace;
5. `preInit` for every mod;
6. `init` for every mod;
7. `postInit` for every mod;
8. `loadComplete` for every mod.

Running the whole lifecycle of one mod before the next would break mods that
expect another mod's preInit to have happened before their init. Handlers are
found by annotation name (not by loading Forge classes) and invoked by
reflection. An exception marks only that mod as failed; the rest continue.

### Registry events and the event bus

A single `EventBus` shim is shared by all legacy loaders (it is one class in the
application classloader). Subscribers are recorded with their owning mod id, so
a registry event fired for mod A does not execute mod B's handlers. This is
necessary because erased `RegistryEvent.Register` parameter types are identical
for blocks, items, sounds and so on; the bus inspects the generic type argument
and the registry name to route correctly. `@SideOnly` is honoured per handler.


## Discovery and ordering

`old_mods/*.jar|zip` are scanned in file-name order. For each jar:

- `mcmod.info` is parsed with Gson (array and object forms accepted);
- the `@Mod` class is found with ASM (`SKIP_CODE`), so no static initializer runs;
- required dependencies and `after` / `before` ordering are extracted from
  `mcmod.info` and the `@Mod.dependencies` mini-language;
- `minecraft`, `forge`, `fml`, `java` are built-ins;
- profile-provided mods (currently the Baubles API surface) satisfy dependencies
  with an explicit warning.

The graph is resolved with a deterministic Kahn topological sort. Missing
required dependencies and cycles disable only the affected mods, with a
diagnostic.

## Adding another version

Implement `LegacyProfile` and add a shim source set:

- `id()`, `minecraftVersion()`, annotation names, lifecycle event names;
- `shimIndexResource()` generated by Gradle;
- `srgMappingsDirectory()` for reobfuscated jars (1.7.10, 1.12.2);
- `providedMods()` for API-only compatibility packages.

The classloader, discovery, dependency resolver, runtime and bridge do not need
to change. Version-specific adapters live in that profile's bridge module.
