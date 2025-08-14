package net.greekboy.greekboymod.block.custom;

import net.greekboy.greekboymod.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class StrawberryCropBlock extends CropBlock
{
    //Variables for the age of the crop
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5; //Done so that all crops don't grow together

    public StrawberryCropBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return ModItems.STRAWBERRY_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty()
    {
        return AGE;
    }

    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(AGE);
    }
}
