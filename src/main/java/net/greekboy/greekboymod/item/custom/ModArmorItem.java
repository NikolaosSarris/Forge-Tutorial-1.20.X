package net.greekboy.greekboymod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.greekboy.greekboymod.item.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ModArmorItem extends ArmorItem
{
    //Map variable the maps a material to a mob effect, bringing them together
    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.SAPPHIRE, new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0,
                            false,false, false)).build();

    public ModArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties)
    {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        if(!pLevel.isClientSide() && pEntity instanceof Player player)
        {
            //Only run the check occasionally to avoid performance issues
            if(pLevel.getGameTime() % 20 == 0)  //Check every second (20 ticks)
            {
                if(hasFullSuitOfArmorOn(player))
                {
                    evaluateArmorEffects(player);
                }
            }
        }
    }

    //Checks the effects that were set to the armor
    private void evaluateArmorEffects(Player player)
    {
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player))
            {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    //Checks if the player has a full set of armor on
    private boolean hasFullSuitOfArmorOn(Player player)
    {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    //Makes sure the armor that the player is wearing is correct
    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player)
    {
        for (ItemStack armorStack : player.getInventory().armor)
        {
            if(!(armorStack.getItem() instanceof ArmorItem))
            {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    //Adds the status effect to the player
    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial,
                                            MobEffectInstance mapStatusEffect)
    {
        MobEffectInstance currentEffect = player.getEffect(mapStatusEffect.getEffect());

        if(hasCorrectArmorOn(mapArmorMaterial, player))
        {
            //Only add/refresh the effect if it doesn't exist or has less than 11 seconds left
            if(currentEffect == null || currentEffect.getDuration() <= 220) //220 ticks = 11 seconds
            {
                player.addEffect(new MobEffectInstance(mapStatusEffect));

            }
        }
    }
}
