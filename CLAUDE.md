# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Minecraft Forge mod, version 1.20.1 (Forge 47.4.6), Java 17.

| Property | Value |
|---|---|
| Mod ID | `greekboymod` |
| Root package | `net.greekboy.greekboymod` |
| Main mod class | `net.greekboy.greekboymod.TutorialMod` |
| Group ID | `net.greekboy.greekboymod` |
| Mod version | `0.1-1.20.1` |

## Build Commands

```bash
./gradlew build        # Compile and produce JAR
./gradlew runClient    # Launch Minecraft client with mod loaded
./gradlew runServer    # Launch dedicated server with mod loaded
./gradlew runData      # Re-generate all JSON assets (models, recipes, loot tables, tags)
```

Generated files land in `src/generated/resources/` — never edit them by hand, always re-run `runData` after changing any datagen provider.

## Package Layout

```
net.greekboy.greekboymod/
├── TutorialMod.java          ← @Mod entry point, wires everything to the event bus
├── block/
│   ├── ModBlocks.java
│   └── custom/               ← Block subclasses (SoundBlock, CornCropBlock, StrawberryCropBlock)
├── item/
│   ├── ModItems.java
│   ├── ModCreativeModeTabs.java
│   ├── ModFoods.java
│   ├── ModToolTiers.java
│   ├── ModArmorMaterials.java
│   └── custom/               ← Item subclasses (MetalDetectorItem, PipeWrenchItem, FuelItem, ModArmorItem)
├── datagen/                  ← All data generation providers
├── event/ModEvents.java      ← Gameplay event handlers (@Mod.EventBusSubscriber)
├── loot/                     ← Global loot modifiers
├── sound/ModSounds.java
├── villager/ModVillagers.java
└── util/ModTags.java         ← Custom TagKey<Block> and TagKey<Item> definitions
```

## Registration Pattern

Every registry type uses `DeferredRegister`. The pattern is identical across all `Mod*` classes:

```java
// 1. Declare the register (static field in the Mod* class)
public static final DeferredRegister<Item> ITEMS =
    DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

// 2. Register entries
public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
    () -> new Item(new Item.Properties()));

// 3. Connect to the event bus (called from TutorialMod constructor)
public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
}
```

**TutorialMod constructor** calls `register(modEventBus)` for every `Mod*` class in this order:
`ModCreativeModeTabs` → `ModItems` → `ModBlocks` → `ModLootModifiers` → `ModVillagers` → `ModSounds`

### Blocks — auto block-item registration

`ModBlocks` uses a private helper so that every block automatically gets a corresponding `BlockItem`:

```java
private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);   // registers the BlockItem in ITEMS
    return toReturn;
}
```

Always use `registerBlock(...)` instead of `BLOCKS.register(...)` directly.

### Sounds — helper keeps ResourceLocation consistent

```java
private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
    return SOUND_EVENTS.register(name, () ->
        SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name)));
}
```

## Creative Tab

There is **one tab**: `ModCreativeModeTabs.TUTORIAL_TAB`. Items and blocks are added inside the `displayItems` lambda using `output.accept(...)`:

```java
public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB =
    CREATIVE_MODE_TABS.register("tutorial_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
            .title(Component.translatable("creativetab.tutorial_tab"))
            .displayItems((params, output) -> {
                output.accept(ModItems.SAPPHIRE.get());
                output.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                // ...
            }).build());
```

The `addCreative` method in `TutorialMod` is **no longer used for adding items** — everything goes directly in the lambda above.

## Resource & Texture Path Conventions

| Asset type | Location |
|---|---|
| Textures (block) | `src/main/resources/assets/greekboymod/textures/block/<name>.png` |
| Textures (item) | `src/main/resources/assets/greekboymod/textures/item/<name>.png` |
| Armor textures | `src/main/resources/assets/greekboymod/textures/models/armor/<name>_layer_1.png` |
| Sounds (.ogg) | `src/main/resources/assets/greekboymod/sounds/<name>.ogg` |
| Sound registry | `src/main/resources/assets/greekboymod/sounds.json` |
| Display names | `src/main/resources/assets/greekboymod/lang/en_us.json` |
| Hand-authored item models | `src/main/resources/assets/greekboymod/models/item/<name>.json` |

Generated block states, block models, and most item models go to `src/generated/resources/` — do not place them in `src/main/resources/`.

### Translation key conventions

```json
"item.greekboymod.<name>": "Display Name"
"block.greekboymod.<name>": "Display Name"
"item.greekboymod.<disc_name>.desc": "Artist - Song Title"   // music disc subtitle (note: dot before desc, not underscore)
"creativetab.tutorial_tab": "GreekBoy Mod"
"tooltip.greekboymod.<name>.tooltip": "Tooltip text"
```

## Data Generation

Providers are registered in `DataGenerators.gatherData()`:

| Provider | Side | Generates |
|---|---|---|
| `ModRecipeProvider` | Server | Shaped, shapeless, smelting, blasting recipes |
| `ModLootTableProvider` | Server | Block drop loot tables |
| `ModBlockTagGenerator` | Server | Block tag JSONs |
| `ModItemTagGenerator` | Server | Item tag JSONs |
| `ModGlobalLootModifiersProvider` | Server | Global loot modifier configs |
| `ModPoiTypeTagsProvider` | Server | POI type tags (villagers) |
| `ModBlockStateProvider` | Client | Block state JSONs |
| `ModItemModelProvider` | Client | Item model JSONs |

### Item model helpers (in `ModItemModelProvider`)

```java
simpleItem(ModItems.FOO);          // flat sprite, parent: item/generated
handheldItem(ModItems.FOO);        // held in hand, parent: item/handheld
evenSimplerBlockItem(ModBlocks.FOO); // uses existing block model as parent
trimmedArmorItem(ModItems.FOO);    // generates all trim override variants
```

### Block state helpers (in `ModBlockStateProvider`)

```java
blockWithItem(ModBlocks.FOO);      // cube_all, registers both block state + item model
stairsBlock((StairBlock) ModBlocks.FOO.get(), blockTexture(ModBlocks.BASE.get()));
slabBlock((SlabBlock) ..., blockTexture(...), blockTexture(...));
fenceBlock((FenceBlock) ..., blockTexture(...));
fenceGateBlock((FenceGateBlock) ..., blockTexture(...));
wallBlock((WallBlock) ..., blockTexture(...));
```

## Custom Base Classes

| Class | Extends | Key override |
|---|---|---|
| `MetalDetectorItem` | `Item` | `useOn()` — scans down for `ModTags.Blocks.METAL_DETECTOR_VALUABLES`, outputs coords to chat |
| `PipeWrenchItem` | `Item` | `getDefaultAttributeModifiers()` — custom damage/speed; `hurtEnemy()`, `mineBlock()` for durability |
| `FuelItem` | `Item` | `getBurnTime()` — configurable burn time passed via constructor |
| `ModArmorItem` | `ArmorItem` | `inventoryTick()` — applies potion effects (Night Vision) when full set worn |
| `SoundBlock` | `Block` | `use()` — plays a `SoundEvent` on right-click |
| `StrawberryCropBlock` | `CropBlock` | 5-stage crop (`AGE_5`); returns strawberry seeds |
| `CornCropBlock` | `CropBlock` | 8-stage two-block-tall crop; overrides `randomTick()`, `growCrops()`, `canSurvive()` |

## Custom Tags (`util/ModTags.java`)

```java
ModTags.Blocks.METAL_DETECTOR_VALUABLES  // blocks the metal detector highlights
ModTags.Blocks.NEEDS_SAPPHIRE_TOOL       // blocks that require a sapphire-tier tool
```

Tags are declared as nested static classes `ModTags.Blocks` and `ModTags.Items`, each with a private `tag(String name)` factory.

## Event Bus Split

Two buses — subscribing to the wrong one silently does nothing:

- **Mod event bus** — registration, lifecycle (`FMLCommonSetupEvent`), `GatherDataEvent`. Subscribe via `modEventBus.addListener(...)` or `@Mod.EventBusSubscriber(bus = Bus.MOD)`.
- **Forge/game event bus** — gameplay events (`VillagerTradesEvent`, entity events, etc.). Subscribe via `MinecraftForge.EVENT_BUS.register(...)` or `@Mod.EventBusSubscriber` (default).

## Adding New Content — Checklist

1. **Register** in `ModItems.java` or via `ModBlocks.registerBlock(...)`.
2. **Add display name** in `src/main/resources/assets/greekboymod/lang/en_us.json`.
3. **Add texture** PNG to the appropriate `textures/` subfolder.
4. **Add to creative tab** via `output.accept(...)` in `ModCreativeModeTabs`.
5. **Add datagen entries**: item model in `ModItemModelProvider`, block state in `ModBlockStateProvider`, recipe in `ModRecipeProvider`, drops in `ModBlockLootTables`.
6. **Run `./gradlew runData`** to regenerate JSON files.