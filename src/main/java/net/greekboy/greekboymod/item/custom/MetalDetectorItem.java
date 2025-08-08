package net.greekboy.greekboymod.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

//Class that inherits from the Item class
public class MetalDetectorItem extends Item
{
    //Constructor of the class
    public MetalDetectorItem(Properties pProperties)
    {
        super(pProperties);
    }

    //A method override to the uson method
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide())
        {
            //Gets the positon of the block clicked on
            BlockPos positionClicked = pContext.getClickedPos();

            //Gets the player object
            Player player = pContext.getPlayer();

            //Boolean for if the block is found
            boolean isFoundBlock = false;

            //Loops through the entire vertical position of the coordinate
            for(int i = 0; i <= positionClicked.getY() + 64; i++)
            {
                //Gets the state of the current block
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                //Checks if the block is valuable
                if (isValuableBlock(state))
                {
                    //Outputs the coordinate to the player
                    outputValueCoordinates(positionClicked.below(i), player, state.getBlock());

                    //Sets the found block boolean to true
                    isFoundBlock = true;
                    break;
                }
            }

            //Checks if the block wasn't found and lets the player know
            if (!isFoundBlock) {
                player.sendSystemMessage(Component.literal("No Valuables Found!"));
            }
        }

        //Damages the item in the player's hand after use
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        //Returns a successful result
        return InteractionResult.SUCCESS;
    }

    private void outputValueCoordinates(BlockPos blockPos, Player player, Block block)
    {
        //Types a message to the player in chat with the specific coordinates
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState state)
    {
        //Returns a boolean based on if the block is iron ore or diamond ore
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE);
    }
}
