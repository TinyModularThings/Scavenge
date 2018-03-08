package scavenge.api;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IClientRegistry
{
	/**
	 * Gets the Items with the ToolClass & minimum required level
	 * @param tool The Tool that should be searched for
	 * @param level The level that is required or bigger
	 * @return a List of all The tools that match that condition
	 */
	public List<ItemStack> getTools(String tool, int level);
}
