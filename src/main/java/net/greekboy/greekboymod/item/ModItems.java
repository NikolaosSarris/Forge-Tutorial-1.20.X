package net.greekboy.greekboymod.item;

import net.greekboy.greekboymod.TutorialMod;
import net.greekboy.greekboymod.item.custom.FuelItem;
import net.greekboy.greekboymod.item.custom.MetalDetectorItem;
import net.greekboy.greekboymod.item.custom.PipeWrenchItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    //Static variable to hold register the items that are to be created
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    //Static variable that holds the Sapphire item to be added to the game
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));

    //Static variable to hold the raw sapphire to be added
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));

    //Static variable to hold the cross
    public static final RegistryObject<Item> HOLY_CROSS = ITEMS.register("holy_cross",
            () -> new Item(new Item.Properties()));

    //Static variable for the metal detector
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    //Static variable for the pipe wrench
    public static final RegistryObject<Item> PIPE_WRENCH = ITEMS.register("pipe_wrench",
            () -> new PipeWrenchItem(new Item.Properties()
                    .durability(250),        // How many uses before it breaks
                    6.0F,                   // Attack damage (6 = 7 hearts total damage)
                    -2.8F                   // Attack speed (-2.8F = 1.2 attacks per second)
            ));

    //Static variable for the strawberry which is food
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));

    //Static variable for the roids bottle which is food
    public static final RegistryObject<Item> ROIDS_BOTTLE = ITEMS.register("roids_bottle",
            () -> new Item(new Item.Properties().food(ModFoods.ROIDS_BOTTLE)));

    //Static variable for the pine cone which is a fuel item
    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));

    //Static variable for the sapphire staff which is a 3D item
    public static final RegistryObject<Item> SAPPHIRE_STAFF = ITEMS.register("sapphire_staff",
            () -> new Item(new Item.Properties().stacksTo(1)));

    //Static variable for sapphire tools
    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE, 4, 2, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE, 1, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE, 7, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE, 0, 0, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE, 0, 0, new Item.Properties()));

    //Static method the register the items
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
