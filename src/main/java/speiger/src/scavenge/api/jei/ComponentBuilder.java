package speiger.src.scavenge.api.jei;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import speiger.src.scavenge.api.misc.MiscUtil;

public class ComponentBuilder
{
	List<List<ItemStack>> requiredItems = new ObjectArrayList<>();
	List<ItemStack> transformBlocks = new ObjectArrayList<>();
	LayerComponent layer;
	
	public ComponentBuilder(String base)
	{
		layer = new LayerComponent(base);
	}
	
	public ComponentBuilder startLayer(String name)
	{
		return startLayer(new TranslationTextComponent(name));
	}
	
	public ComponentBuilder startLayer(String name, Object...args)
	{
		return startLayer(new TranslationTextComponent(name, args));
	}
	
	public ComponentBuilder startLayer(ITextComponent name)
	{
		LayerComponent child = new LayerComponent(name);
		layer.addChild(child);
		layer = child;
		return this;
	}
	
	public ComponentBuilder addText(String name)
	{
		return addText(new TranslationTextComponent(name));
	}
	
	public ComponentBuilder addText(String name, Object...args)
	{
		return addText(new TranslationTextComponent(name, args));
	}
	
	public ComponentBuilder addText(ITextComponent name)
	{
		layer.addChild(new DisplayComponent(name));
		return this;
	}
	
	public ComponentBuilder addRequiredItem(ItemStack stack, InventoryType type)
	{
		stack = stack.copy();
		MiscUtil.addTooltip(stack, new TranslationTextComponent("scavenge.added.by.requirement", layer.getName()));
		MiscUtil.addTooltip(stack, type.getName());
		requiredItems.add(ObjectLists.singleton(stack));
		return this;
	}
	
	public ComponentBuilder addRequiredItems(List<ItemStack> items, InventoryType type)
	{
		List<ItemStack> result = new ObjectArrayList<>();
		for(int i = 0,m=items.size();i<m;i++)
		{
			ItemStack stack = items.get(i).copy();
			MiscUtil.addTooltip(stack, new TranslationTextComponent("scavenge.added.by.requirement", layer.getName()));
			MiscUtil.addTooltip(stack, type.getName());
			result.add(stack);
		}
		requiredItems.add(result);
		return this;
	}
	
	public ComponentBuilder addBlockTransform(BlockState state, double chance)
	{
		if(chance <= 0D) return this;
		ItemStack stack = new ItemStack(state.getBlock());
		MiscUtil.addTooltip(stack, new TranslationTextComponent("scavenge.added.by.requirement", layer.getName()));
		if(chance < 100) MiscUtil.addTooltip(stack, new TranslationTextComponent("scavenge.chance", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(chance)));
		transformBlocks.add(stack);
		return this;
	}
	
	public ComponentBuilder finishLayer()
	{
		layer = layer.parent;
		return this;
	}
	
	public LayerComponent getResult()
	{
		while(layer.parent != null) layer = layer.parent;
		return layer;
	}
	
	public List<List<ItemStack>> getRequiredItems()
	{
		return requiredItems;
	}
	
	public List<ItemStack> getTransformBlocks()
	{
		return transformBlocks;
	}
	
	public static enum InventoryType
	{
		HAND("scavenge.inv.hand"),
		PLAYER("scavenge.inv.player"),
		BLOCK("scavenge.inv.block");
		
		ITextComponent name;
		
		private InventoryType(String name)
		{
			this.name = new TranslationTextComponent(name);
		}
		
		public ITextComponent getName()
		{
			return name;
		}
	}
}