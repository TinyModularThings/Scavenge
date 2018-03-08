package scavenge.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IResourceEffect extends IResourceProperty
{
	/**
	 * Function that applies the Effect
	 * @param state The Block Thats clicked
	 * @param world The World the Block Is clicked in
	 * @param pos The Position the Block is in
	 * @param player The Player who clickt it
	 * @param left if it was a left or rightclick
	 * @param side which side the block was clicked on
	 * @param resourceID the Handler that calls it (Defined by the users)
	 * @param container The Container that is sending data to the loot  generator
	 * @return true = will send a message if defined false = no message
	 */
	public boolean applyEffects(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean left, EnumFacing side, String resourceID, EffectContainer container);
}
