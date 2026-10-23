# Compatibility measurements

Measurements below come from the headless probes on a large real-world 1.12.2
mod jar. They size the shim work; they are not a claim of gameplay parity.

## Jar profile

| Metric | Value |
|---|---|
| class files | 1120 |
| non-client classes | 898 |
| resource files (assets/data) | 1562 |
| public API classes (`api/`) | 176 |
| access transformer file | present (`FMLAT` manifest attribute) |
| CoreMod / mixin config | none |
| declared dependency | one third-party API, emulated as an API-only package |

## Static reference profile

| Metric | Value |
|---|---|
| distinct Minecraft/Forge types referenced | 605 |
| client-side types referenced | 110 |
| types covered by current shims | 410 |
| types still referenced but not shimmed | 195 |
| distinct external API types | 7 (covered by the API shim package) |

The 605 figure comes from constant-pool class references, so it is an upper
bound: some constants are only used in dead code. A type can be absent from the
shim set and still not break class loading because method-body references are
resolved lazily.

## Class loading

With 542 shims:

| Probe | Loaded | Failed |
|---|---|---|
| non-client classes | 898 / 898 | 0 |
| including client classes | 1078 / 1120 | 42 |

Remaining client failures are rendering internals (atlas sprites, GUI widgets,
extra model classes) plus two shaded-library references (`javax.vecmath`,
`scala.NotImplementedError`).

## Lifecycle

The headless lifecycle probe (server side, recording bridge) completes:

```
state after construct: CONSTRUCTED
state after preInit:   PRE_INIT
registered blocks:     200
registered items:      299
state after init:      INIT
state after postInit:  POST_INIT
```

This means the mod's `preInit`, `init` and `postInit` handlers run to
completion, including capabilities registration, networking registration,
config sync, block/item constructors, registry events, ore dictionary,
`ForgeRegistries`, fluids and research metadata.

Item and block semantics are mapped on registration:

- block items become modern `BlockItem` / `LegacyBlockItem`;
- armor uses `Item.Properties.humanoidArmor` with the matching slot and material;
- swords, pickaxes, axes, shovels and hoes use the modern tool properties and
  the mapped material (wood/stone/iron/gold/diamond/netherite);
- food uses `FoodProperties` (nutrition, saturation, always-edible);
- slabs and stairs use `SlabBlock` / `StairBlock` adapters with all modern state
  variants generated into their blockstates.

### What is actually registered

200 blocks and 299 items reach the bridge. They are not yet proven against the
real modern registry: the headless probe uses a recording bridge. The next
validation step is an in-game boot, where `FabricBridge` maps them to
`BuiltInRegistries` and `LegacyBlockAdapter` / `BlockItem`.

## Assets, models and names

Legacy assets are copied into a generated pack under
`config/legacyloader/resourcepacks/<modid>/` and mounted as an always-enabled
Fabric builtin resource pack. The original jar stays read-only.

Conversion steps:

- `textures/blocks` -> `textures/block` and `textures/items` -> `textures/item`
  (modern atlases only scan the singular directories);
- all model texture references are rewritten from `:blocks/` / `:items/` to
  `:block/` / `:item/`;
- 1.12 `.lang` files are converted to `.json`;
- `forge_marker` blockstates are replaced by a single-variant modern blockstate
  and a generated block model (`cube_all` fallback or a model derived from the
  legacy `defaults` section);
- modern `assets/<ns>/items/<name>.json` item model definitions are generated
  (1.21.4+ format), including a fallback model when only a texture exists;
- legacy armor materials are mapped to custom equipment assets: old
  `textures/entity/armor/<material>_1.png` / `_2.png` are copied into
  `textures/entity/equipment/humanoid/` and `humanoid_leggings/`, with an
  `equipment/legacy_<material>.json` description, so worn armor keeps its
  original look instead of falling back to iron/diamond;
- legacy translation keys (`tile.*.name`, `item.*.name`) are used as item/block
  description ids, so lang entries resolve to real names.

A dedicated "Legacy Mods" creative tab is registered with all successfully
registered items.

Known asset limits: custom `IModel`/OBJ/TESR renderers and models that use a
model as a texture (for example a chest model path in `textures`) are not
translated yet.

## Client side

Client proxy construction still needs a client-rendering shim set
(`ClientRegistry`, `ModelLoader`, renderer registries, shader/GL entry points).
The loader can force the server proxy on a client:

```bash
-Dlegacyloader.forceServerProxy=true
```

This preserves registration and gameplay-side code while skipping client-only
renderers, key bindings and models.

## Registry model

The jar registers content through `RegistryEvent.Register<T>` and
`@Mod.EventBusSubscriber`, not `GameRegistry`. The event bus now:

- scans and registers annotated subscriber classes;
- filters handlers by the generic registry type (blocks/items/sounds/potions/
  biomes/entities/recipes), because erased parameter types are identical;
- honours `@SideOnly`;
- dispatches in priority order and isolates handler failures.

`gradle runRegistryProbe` verifies the path end to end without Minecraft.

## Access transformers

The jar declares an access transformer file. In Forge it widens visibility of
Minecraft members. Here old code links against shims, so the shim itself can
expose the members publicly. No bytecode access transformation of the modern
game is needed.

## Conclusion

The loader now has real class-load coverage and a complete server-side
lifecycle for a large 1.12.2 jar. The remaining gaps are behavioural, not
structural:

1. in-game validation of modern registry mapping and item/block properties;
2. tile entity / entity / world adapters (types exist, behaviour does not);
3. NBT and capability persistence;
4. networking translation;
5. the client rendering shim set.

These are additive: each one replaces a stub or a degraded report, without
changing the classloader, discovery or lifecycle design.
