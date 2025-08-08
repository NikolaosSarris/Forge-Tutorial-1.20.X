package net.greekboy.greekboymod.item;

import net.greekboy.greekboymod.TutorialMod;
import net.greekboy.greekboymod.item.custom.MetalDetectorItem;
import net.greekboy.greekboymod.item.custom.PipeWrenchItem;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> PIPE_WRENCH = ITEMS.register("pipe_wrench",
            () -> new PipeWrenchItem(new Item.Properties()
                    .durability(250),        // How many uses before it breaks
                    6.0F,                   // Attack damage (6 = 7 hearts total damage)
                    -2.8F                   // Attack speed (-2.8F = 1.2 attacks per second)
            ));

    //Static method the register the items
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
