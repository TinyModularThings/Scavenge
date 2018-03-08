package scavenge.api.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.text.TextFormatting;

public class LootUtil
{
	public static boolean isStackEmpty(ItemStack stack)
	{
		return stack == null || stack.stackSize <= 0;
	}
	
	public static void setStackSize(ItemStack stack, int amount)
	{
		stack.stackSize = amount;
	}
	
	public static void changeStackSize(ItemStack stack, int amount)
	{
		stack.stackSize += amount;
	}
	
	public static int getStackSize(ItemStack stack)
	{
		return stack.stackSize;
	}
	
	public static ItemStack getNullStack()
	{
		return null;
	}
	
	public static void addToolTip(ItemStack stack, String tooltip)
	{
		NBTTagCompound nbt = stack.getSubCompound("display", true);
		NBTTagList list = nbt.getTagList("Lore", 8);
		nbt.setTag("Lore", list);
		list.appendTag(new NBTTagString(TextFormatting.RESET+tooltip));
	}
	
	public static boolean doesStackMatch(ItemStack stack, Item item, int meta, NBTTagCompound nbt)
	{
		if(isStackEmpty(stack) || stack.getItem() != item)
		{
			return false;
		}
		if(meta != Short.MAX_VALUE && stack.getMetadata() != meta)
		{
			return false;
		}
		if(nbt != null && !NBTUtil.areNBTEquals(nbt, stack.getTagCompound(), true))
		{
			return false;
		}
		return true;
	}
}
