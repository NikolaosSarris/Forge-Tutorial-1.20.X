# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft Forge mod for version 1.20.1 (Forge 47.4.6), built with Java 17. Mod ID: `greekboymod`, root package: `net.greekboy.greekboymod`.

## Build Commands

```bash
# Build the mod JAR
./gradlew build

# Run Minecraft client with the mod loaded
./gradlew runClient

# Run a dedicated server with the mod loaded
./gradlew runServer

# Re-generate all data files (block states, item models, recipes, loot tables, tags)
./gradlew runData
```

> After running `runData`, generated files land in `src/generated/resources/`. These are committed and referenced at runtime — always regenerate after changing any datagen provider.

## Architecture

### Registration Pattern

Every registry type (items, blocks, sounds, etc.) uses Forge's `DeferredRegister` pattern:

1. A static `DeferredRegister<T>` is created in the relevant `Mod*` class.
2. Entries are registered via `REGISTER.register("name", () -> new Thing(...))`.
3. The register is connected to the event bus by calling `ModFoo.register(modEventBus)` inside `TutorialMod`'s constructor.

### Key Files

| File | Purpose |
|---|---|
| `TutorialMod.java` | Mod entry point; wires all `Mod*` classes to the event bus |
| `item/ModItems.java` | All item registrations |
| `block/ModBlocks.java` | All block registrations |
| `sound/ModSounds.java` | Sound event registrations |
| `villager/ModVillagers.java` | Custom POI types and villager professions |
| `event/ModEvents.java` | Gameplay event handlers (e.g. villager trades) |
| `util/ModTags.java` | Custom `TagKey<Block>` and `TagKey<Item>` definitions |
| `item/ModToolTiers.java` | Tool tier (mining level/speed/durability) |
| `item/ModArmorMaterials.java` | Armor material definitions |
| `item/ModFoods.java` | `FoodProperties` constants |

### Data Generation

All JSON assets (recipes, block states, item models, loot tables, tags) are auto-generated. Providers live in `datagen/`:

- `ModRecipeProvider` — shaped/shapeless/smelting recipes
- `ModBlockStateProvider` — blockstate JSON
- `ModItemModelProvider` — item model JSON
- `ModBlockLootTables` — block drop tables
- `ModBlockTagGenerator` / `ModItemTagGenerator` — tag JSON
- `DataGenerators.java` — `GatherDataEvent` subscriber that wires them all together

Manually authored JSON (textures, sounds, `en_us.json`) lives in `src/main/resources/assets/greekboymod/`. Generated JSON lives in `src/generated/resources/` — never edit generated files by hand.

### Adding New Content (Typical Workflow)

1. **Register** the item/block in `ModItems.java` or `ModBlocks.java` using `DeferredRegister`.
2. **Add a display name** in `src/main/resources/assets/greekboymod/lang/en_us.json`.
3. **Add to creative tab** in `ModCreativeModeTabs.java` inside `addCreative()`.
4. **Add datagen entries** in the relevant provider(s) so models, recipes, and loot tables are generated.
5. **Run `./gradlew runData`** to regenerate the JSON files.

### Custom Mechanics

- **MetalDetectorItem** — overrides `useOn()` to scan downward for blocks tagged `metal_detector_valuables` and prints coordinates to chat.
- **SoundBlock** — overrides `use()` to play a sound event on right-click.
- **CornCropBlock / StrawberryCropBlock** — extend `CropBlock` with custom stage counts and seed item linkage.
- **ModArmorItem** — extends `ArmorItem` to apply potion effects to the wearer while equipped.
- **AddItemModifier / AddSusSandItemModifier** — global loot modifiers that inject items into existing loot tables.

### Event Bus Split

Forge uses two event buses; subscribing to the wrong one silently does nothing:

- **Mod event bus** (`FMLCommonSetupEvent`, registration events, `GatherDataEvent`) — subscribe via `modEventBus.addListener(...)` or `@Mod.EventBusSubscriber(bus = Bus.MOD)`.
- **Forge/game event bus** (`VillagerTradesEvent`, entity events, etc.) — subscribe via `MinecraftForge.EVENT_BUS.register(...)` or `@Mod.EventBusSubscriber` (default bus).