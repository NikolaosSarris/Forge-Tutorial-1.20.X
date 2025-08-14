package net.greekboy.greekboymod.datagen.loot;

import net.greekboy.greekboymod.TutorialMod;
import net.greekboy.greekboymod.item.ModItems;
import net.greekboy.greekboymod.loot.AddItemModifier;
import net.greekboy.greekboymod.loot.AddSusSandItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider
{
    public ModGlobalLootModifiersProvider(PackOutput output)
    {
        super(output, TutorialMod.MOD_ID);
    }

    @Override
    protected void start()
    {
        //Add drop from a block that uses random chance
        add("pine_cone_from_grass", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()},
                ModItems.PINE_CONE.get()));

        //Add drop from a mob that is 100%
        add("pine_cone_from_creeper", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", "entities/creeper")).build()},
                ModItems.PINE_CONE.get()));

        //Add drop that can be found in a jungle temple with 100% certainty
        add("metal_detector_from_jungle_temples", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/jungle_temple")).build()},
                ModItems.METAL_DETECTOR.get()));

        add("metal_detector_from_suspicious_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology_desert_pyramid")).build()},
                ModItems.METAL_DETECTOR.get()));
    }
}
