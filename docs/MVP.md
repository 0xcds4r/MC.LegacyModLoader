# MVP roadmap

Status of the current tree is marked with `[x]`.

## Phase 0 - boot skeleton (done)

- `[x]` scan `old_mods/` for `.jar` / `.zip` without loading code;
- `[x]` parse `mcmod.info` (array and object form);
- `[x]` locate `@Mod` with ASM, read `dependencies`;
- `[x]` deterministic topological load order, cycle and missing-dep reporting;
- `[x]` one child-first classloader per mod, blocked modern namespaces;
- `[x]` generated shim index, parent-first for API classes;
- `[x]` SRG -> MCP bytecode remapping (1.12.2 production jars);
- `[x]` construct `@Mod`, inject `@Mod.Instance`, `@SidedProxy`, `@Mod.Metadata`;
- `[x]` emulate `preInit`, `init`, `postInit`, `loadComplete` across all mods;
- `[x]` `GameRegistry.register(Block/Item)` -> modern registry entries;
- `[x]` fail-soft per-mod error handling;
- `[x]` headless compatibility probe.

Acceptance: a trivial 1.12.2 mod with one block, one item and lifecycle logging
loads, registers both entries and does not crash the game.

## Phase 1 - common API surface (substantially done)

Goal: make the probe load most non-client classes of a real mod.

- `[x]` core game types: `NBTBase`, `NBTTagList`, `NonNullList`, `IProperty`,
  `IBlockAccess`, `ITickable`, `Container`, `Slot`, `IInventory`,
  `EntityMob` / `EntityCreature` / AI base classes, `RayTraceResult`, `Vec3i`,
  `DamageSource`, `Potion`, `ItemArmor` / tiered tools, `EnumRarity`;
- `[x]` Forge: `Capability`, `CapabilityManager`, `ICapabilityProvider`,
  `ICapabilitySerializable`, `INBTSerializable`, `IItemHandler`, `oredict`,
  `IEntityAdditionalSpawnData`;
- `[x]` block state/default state plumbing and `IBlockState` values;
- `[ ]` tile entity ticking bridge (types and registration exist, behaviour not);
- `[x]` dimension/world stubs with explicit unsupported errors.

Acceptance met for class loading: **898 / 898 non-client classes** of the
reference jar load.

## Phase 2 - registry events and event bus (done)

- `[x]` `IForgeRegistry` shim backed by the bridge;
- `[x]` `RegistryEvent.Register<Block/Item/...>` dispatch after construction;
- `[x]` generic registry-kind filtering (a sound handler does not run on the
  item event);
- `[x]` scan and register `@Mod.EventBusSubscriber` classes;
- `[x]` `@SubscribeEvent` dispatch with parameter-type matching, priority and
  `@SideOnly` filtering;
- `[x]` `ForgeRegistries` static handles;
- `[x]` self-test: `gradle runRegistryProbe`.

Acceptance met: the reference jar registers **200 blocks and 299 items** during
the emulated registry events.

## Phase 3 - NBT, capabilities, data

- `[ ]` real NBT bridge (legacy `NBTTagCompound` <-> modern components/tags);
- `[ ]` capability storage, provider lookup, player capabilities;
- `[ ]` item stack metadata, damage and NBT translation.

Acceptance: a mod stores and reads a value on a player and on a tile entity
across a save/load cycle.

## Phase 4 - world, entities, tile entities

- `[ ]` `World` reads/writes through a modern `Level` adapter;
- `[ ]` block update and neighbour hooks;
- `[ ]` tile entity adapters (tick, NBT, sync);
- `[ ]` entity adapters (spawn, tick, data watcher);
- `[ ]` `ITickable`, `IInventory`, containers.

Acceptance: a simple machine block ticks, stores inventory and survives save/load.

## Phase 5 - networking and GUIs

- `[ ]` `SimpleNetworkWrapper` -> modern custom payloads;
- `[ ]` discriminator and side routing;
- `[ ]` `IGuiHandler` -> modern menu/screen opening;
- `[ ]` `FMLInterModComms` delivery between legacy mods.

Acceptance: a client/server round-trip works on a dedicated server.

## Phase 6 - client rendering and assets

- `[ ]` mount legacy `assets/` as a generated resource pack;
- `[ ]` block/item model JSON compatibility;
- `[ ]` `ModelResourceLocation`, `ItemMeshDefinition`, `StateMapperBase`;
- `[ ]` TESR/entity renderer adapters;
- `[ ]` LWJGL2 `org.lwjgl.util.*` compatibility for immediate-mode code.

Acceptance: registered blocks and items render with legacy textures; simple
TESRs draw.

## Phase 7 - recipes, research-like systems, polish

- `[ ]` shaped/shapeless/smelting recipe translation;
- `[ ]` ore dictionary -> tags;
- `[ ]` loot tables and world generation hooks;
- `[ ]` diagnostics UI (`/legacyloader status`, `/legacyloader report`);
- `[ ]` config to disable individual mods and individual bridge features.

## Portability

The classloader, discovery, dependency resolver and lifecycle contain no Fabric
types except the entry point and `FabricLoader` used for paths/environment.
A NeoForge port only needs: a new entry point, a NeoForge bridge implementation,
and the same shim jars.
