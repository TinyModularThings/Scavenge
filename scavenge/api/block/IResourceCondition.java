package scavenge.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IResourceCondition extends IResourceProperty
{
	/**
	 * Function allows to check if a condition.
	 * @param state The Block that is being clickt on
	 * @param world the World the block is in
	 * @param pos the Position the Block is in
	 * @param player the Player who clickt it
	 * @param leftClick if it was a left / right click
	 * @param side which side the block is being clickt on
	 * @param resourceID the ID the script used
	 * @param client if it is clientsided or server sided
	 * @return if the condition is true or false
	 */
	public boolean canInteract(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean leftClick, EnumFacing side, String resourceID, boolean client);
}
