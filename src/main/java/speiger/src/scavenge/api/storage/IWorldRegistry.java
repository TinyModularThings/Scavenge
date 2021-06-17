package speiger.src.scavenge.api.storage;

import net.minecraft.world.World;

public interface IWorldRegistry
{
	public IWorldStorage getWorldStorage(World world);
	public IGlobalStorage getGlobalStorage();
}
