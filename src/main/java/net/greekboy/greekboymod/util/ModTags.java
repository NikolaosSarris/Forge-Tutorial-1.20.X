package net.greekboy.greekboymod.util;

import net.greekboy.greekboymod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags
{
    //Class to have the tags for blocks
    public static class Blocks
    {
        //Variable that holds the tag for the specific metal detector valuables
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");

        //Variable that holds the tag for what needs a sapphire tool
        public static final TagKey<Block> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");

        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
        }
    }

    //Class to have the tags for items
    public static class Items
    {
        private static TagKey<Item> tag(String name)
        {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
        }
    }
}
