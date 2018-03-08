package scavenge.api.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import scavenge.api.utils.IntCounter;

public interface IWorldHandler
{
	public IDelivered<IntCounter> getCounter(String id);
	
	public IDelivered<IntCounter> getCounterPerBlock(String id, BlockPos pos);
	
	public IDelivered<IntCounter> getCounterPerChunk(String id, BlockPos pos);
	
	public IDelivered<NBTTagCompound> getCustomData(String id);
	
	public IDelivered<NBTTagCompound> getCustomDataPerBlock(String id, BlockPos pos);
	
	public IDelivered<NBTTagCompound> getCustomDataPerChunk(String id, BlockPos pos);
	
	public void markBlockAsUnbreakable(String id, BlockPos pos);
	
	public void removeBlockUnbreaking(String id, BlockPos pos);
	
	public void removeBlockUnbreaking(BlockPos pos);
	
	public boolean isUnbreakable(BlockPos pos, IBlockState state);
}
