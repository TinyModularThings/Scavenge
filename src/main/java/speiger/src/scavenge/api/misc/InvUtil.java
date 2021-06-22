package speiger.src.scavenge.api.misc;

import java.util.function.Predicate;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InvUtil
{
	public static int countItems(IInventory inv, Predicate<ItemStack> test, int limit, boolean includeEmpty)
	{
		int total = 0;
		for(int i = 0,m=inv.getContainerSize();i<m;i++)
		{
			ItemStack stack = inv.getItem(i);
			if(stack.isEmpty() && !includeEmpty) continue;
			if(test.test(stack))
			{
				total += stack.getCount();
				if(limit > 0 && total >= limit) return total;
			}
		}
		return total;
	}
	
	public static int countItems(IItemHandler inv, Predicate<ItemStack> test, int limit, boolean includeEmpty)
	{
		int total = 0;
		for(int i = 0,m=inv.getSlots();i<m;i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty() && !includeEmpty) continue;
			if(test.test(stack))
			{
				total += stack.getCount();
				if(limit > 0 && total >= limit) return total;
			}
		}
		return total;
	}
	
	public static void consumeItems(IInventory inv, Predicate<ItemStack> test, int limit)
	{
		for(int i = 0,m=inv.getContainerSize();i<m;i++)
		{
			ItemStack stack = inv.getItem(i);
			if(stack.isEmpty()) continue;
			if(test.test(stack))
			{
				limit -= inv.removeItem(i, limit).getCount();
				if(limit <= 0) return;
			}
		}
	}
	
	public static void consumeItems(IItemHandler inv, Predicate<ItemStack> test, int limit)
	{
		for(int i = 0,m=inv.getSlots();i<m;i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty()) continue;
			if(test.test(stack))
			{
				limit -= inv.extractItem(i, limit, false).getCount();
				if(limit <= 0) return;
			}
		}
	}
}
