# Legacy Mod Loader

Runs unmodified Forge 1.12.2 mod jars on modern Minecraft (Fabric 26.3) through
an isolated classloader and a versioned shim layer.

The original jars in `old_mods/` are only ever opened read-only. Nothing is
patched, rewritten or repacked on disk.

## Status

Working end to end for the server-side lifecycle of a large real-world jar:

- 898 / 898 non-client classes load through the isolated loader (0 failures);
- 1078 / 1120 classes including client classes load (42 failures, client
  rendering internals remain);
- `construct` -> `preInit` -> `init` -> `postInit` completes;
- registry events register 200 blocks and 299 items through the bridge;
- SRG -> MCP remapping, `@Mod.Instance`, `@SidedProxy`, `@Mod.Metadata` and
  `@Mod.EventBusSubscriber` are implemented.

Client proxy construction still needs a client-rendering shim set. As a
temporary workaround the loader can force the server proxy on a client:

```bash
-Dlegacyloader.forceServerProxy=true
```

That boots and registers content without client-only rendering/keys.

## Build

```bash
gradle build
# jar: build/libs/legacy-mod-loader-0.1.0.jar
```

Java 25 is required (Minecraft 26.3 runs on Java 25).

## Install

1. Build the jar.
2. Put it into the instance `mods/` directory.
3. Put original 1.12.2 mod jars into `old_mods/` in the game directory.
4. Start the game and check `logs/latest.log` for the `legacyloader` lines.

## Development probes

```bash
# class-load coverage for any legacy jar
gradle runCompatibilityProbe --args="/path/to/mod.jar"
gradle runCompatibilityProbe --args="/path/to/mod.jar --include-client"

# registry event / event bus self-test (no Minecraft needed)
gradle runRegistryProbe

# construct + full server lifecycle against a recording bridge (no Minecraft)
gradle runLifecycleProbe --args="/path/to/mod.jar"
gradle runLifecycleProbe --args="/path/to/mod.jar --client"
```

The probes are development tools and are not shipped in the mod jar.

## Layout

```
src/main/java/dev/legacyloader/
  LegacyModLoader.java        Fabric entry point, fail-soft boot
  LegacyLoaderConfig.java     paths and feature flags
  api/                        version-independent profile contract
  discovery/                  scanning, mcmod.info, dependency graph
  classloader/                isolation, SRG remapping, shim index
  runtime/                    containers, lifecycle, injection
  bridge/                     narrow modern <-> legacy interface
  profile/                    Forge 1.12.2 profile
src/shim1122/java/            binary-compatible 1.12.2 shims
src/probe/java/               headless probes
docs/                         architecture and roadmap
```

## Design documents

- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md)
- [`docs/MVP.md`](docs/MVP.md)
- [`docs/COMPATIBILITY.md`](docs/COMPATIBILITY.md)
