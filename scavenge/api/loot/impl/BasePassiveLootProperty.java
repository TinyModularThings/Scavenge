
package scavenge.api.loot.impl;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BasePassiveLootProperty extends BaseLootProperty
{
	
	public BasePassiveLootProperty(String id)
	{
		super(id, false);
	}
	
	@Override
	public ItemStack applyActiveEffect(ItemStack stack, NBTTagCompound customData)
	{
		return stack;
	}
	
	
	@Override
	public List<ItemStack> applyMultiInfoEffect(ItemStack stack)
	{
		return Arrays.asList(stack);
	}
	
}
