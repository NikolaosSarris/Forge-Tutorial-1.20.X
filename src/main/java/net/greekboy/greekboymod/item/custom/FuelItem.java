package net.greekboy.greekboymod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class FuelItem extends Item
{
    //Variable for the burn time
    private int burnTime = 0;

    //Constructor taking in properties and a burn time
    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }

    //Returns the burn time value
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
