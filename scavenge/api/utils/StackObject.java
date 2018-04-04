package scavenge.api.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StackObject
{
	Item item;
	int meta;
	int stacksize;
	NBTTagCompound nbt;
	
	public StackObject(Item item, int meta, int stacksize, NBTTagCompound nbt)
	{
		this.item = item;
		this.meta = meta;
		this.stacksize = stacksize;
		this.nbt = nbt;
	}
	
	public StackObject copy()
	{
		return new StackObject(item, meta, stacksize, nbt);
	}
	
	public String getName()
	{
		ItemStack stack = new ItemStack(item, 1, meta == Short.MAX_VALUE ? 0 : meta);
		stack.setTagCompound(nbt);
		return stack.getDisplayName();
	}
	
	public int getStackize()
	{
		return stacksize;
	}
	
	public void decreaseStackSize(int amount)
	{
		stacksize -= amount;
	}
	
	public boolean matches(ItemStack stack)
	{
		return LootUtil.doesStackMatch(stack, item, meta, nbt);
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getMeta()
	{
		return meta;
	}
	
	public NBTTagCompound getNbt()
	{
		return nbt;
	}
}
