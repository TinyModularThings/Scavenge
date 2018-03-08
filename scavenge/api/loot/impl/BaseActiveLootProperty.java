package scavenge.api.loot.impl;

import net.minecraft.item.ItemStack;

public abstract class BaseActiveLootProperty extends BaseLootProperty
{
	public BaseActiveLootProperty(String id)
	{
		super(id, true);
	}
	
	@Override
	public ItemStack applyPassiveEffect(ItemStack stack)
	{
		return stack;
	}
	
	@Override
	public ItemStack applyInfoEffect(ItemStack stack)
	{
		return stack;
	}
}
