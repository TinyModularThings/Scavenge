package speiger.src.scavenge.api.storage;

import org.apache.commons.lang3.mutable.MutableLong;

import net.minecraft.nbt.CompoundNBT;

public interface IGlobalStorage
{
	public IInteractable<MutableLong> getCounter(String id);
	public IInteractable<CompoundNBT> getData(String id);

}
