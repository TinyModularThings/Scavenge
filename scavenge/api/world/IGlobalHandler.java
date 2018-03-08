package scavenge.api.world;

import net.minecraft.nbt.NBTTagCompound;
import scavenge.api.utils.IntCounter;

public interface IGlobalHandler
{
	public IDelivered<IntCounter> getCounter(String id);
	
	public IDelivered<NBTTagCompound> getCustomData(String id);
}
