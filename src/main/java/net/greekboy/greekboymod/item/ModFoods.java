package net.greekboy.greekboymod.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods
{
    //Variable to hold the strawberry food item
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2)
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();

    public static final FoodProperties ROIDS_BOTTLE = new FoodProperties.Builder().nutrition(8)
            .saturationMod(0.5f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 10000), 1f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 10000), 1f)
            .build();
}
