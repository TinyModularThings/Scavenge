package scavenge.api.utils;

import com.google.common.base.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockEntry
{
	Block block;
	int meta;
	
	public BlockEntry(IBlockState state)
	{
		this(state.getBlock(), state.getBlock().getMetaFromState(state));
	}
	
	public BlockEntry(Block block, int meta)
	{
		this.block = block;
		this.meta = meta;
	}
	
	public boolean matches(IBlockState state)
	{
		if(block != state.getBlock())
		{
			return false;
		}
		if(meta == Short.MAX_VALUE)
		{
			return true;
		}
		return meta == block.getMetaFromState(state);
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public int getMeta()
	{
		return meta;
	}
	
	public boolean hasItemBlock()
	{
		return Item.getItemFromBlock(block) != Items.AIR;
	}
	
	public ItemStack toStack()
	{
		return new ItemStack(block, 1, meta);
	}
	
	public String getName()
	{
		return hasItemBlock() ? toStack().getDisplayName() : block.getLocalizedName();
	}
	
	public boolean isBlank()
	{
		return meta == Short.MAX_VALUE;
	}
	
	public IBlockState getBlockState()
	{
		if(isBlank())
		{
			return block.getDefaultState();
		}
		return block.getStateFromMeta(meta);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hashCode(block, meta);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof BlockEntry)
		{
			BlockEntry other = (BlockEntry)obj;
			return other.block == block && other.meta == meta;
		}
		return false;
	}
}
