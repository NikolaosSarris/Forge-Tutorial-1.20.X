package net.greekboy.greekboymod.item;

import net.greekboy.greekboymod.TutorialMod;
import net.greekboy.greekboymod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs
{
    //Static variable to hold the new creative mode tab
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TutorialMod.MOD_ID);

    //Static variable that creates the creative mode tab and populates it with specific items
    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.tutorial_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //Gets the custom item / block to display in the tab
                        output.accept(ModItems.SAPPHIRE.get());
                        output.accept(ModItems.RAW_SAPPHIRE.get());

                        output.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                        output.accept(ModBlocks.RAW_SAPPHIRE_BLOCK.get());

                        output.accept(ModBlocks.SAPPHIRE_ORE.get());
                        output.accept(ModBlocks.DEEPLSATE_SAPPHIRE_ORE.get());
                        output.accept(ModBlocks.NETHER_SAPPHIRE_ORE.get());
                        output.accept(ModBlocks.END_STONE_SAPPHIRE_ORE.get());

                        output.accept(ModItems.HOLY_CROSS.get());

                        output.accept(ModItems.METAL_DETECTOR.get());

                        output.accept(ModItems.PIPE_WRENCH.get());

                        output.accept(ModBlocks.SOUND_BLOCK.get());

                        output.accept(ModItems.STRAWBERRY.get());
                        output.accept(ModItems.ROIDS_BOTTLE.get());

                        output.accept(ModItems.PINE_CONE.get());

                    })
                    .build());

    //Static method that registers the new creative mode tab
    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
