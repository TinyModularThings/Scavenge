package speiger.src.scavenge.api.storage;

import org.apache.commons.lang3.mutable.MutableLong;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public interface IWorldStorage
{
	public IInteractable<MutableLong> getWorldCounter(String id);
	public IInteractable<MutableLong> getChunkCounter(String id, BlockPos pos);
	public IInteractable<MutableLong> getBlockCounter(String id, BlockPos pos);
	
	public IInteractable<CompoundNBT> getWorldData(String id);
	public IInteractable<CompoundNBT> getChunkData(String id, BlockPos pos);
	public IInteractable<CompoundNBT> getBlockData(String id, BlockPos pos);
}
