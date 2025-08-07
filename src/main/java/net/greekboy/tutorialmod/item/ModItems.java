package net.greekboy.tutorialmod.item;

import net.greekboy.tutorialmod.TutorialMod;
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

    //Static method the register the items
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
