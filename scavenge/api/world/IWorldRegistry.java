package scavenge.api.world;

import java.util.Set;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWorldRegistry
{
	public IWorldHandler getStorageForWorld(World world);
	
	public IGlobalHandler getGlobalHandler(World world);
	
	public Set<String> getStructures(World world);
	
	public boolean isInsideStructure(World world, String structure, BlockPos position);
}
