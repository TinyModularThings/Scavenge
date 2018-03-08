package scavenge.api.block;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class EffectContainer
{
	int lootRolls = 1;
	int luckPoints = 0;
	float luckModifier = 1F;
	boolean dropItems = false;
	NBTTagCompound customData = new NBTTagCompound();
	
	public void addDropRolls(int value)
	{
		lootRolls+=value;
	}
	
	public void addLuckPoints(int value)
	{
		luckPoints+=value;
	}
	
	public void addLuckModifier(float value)
	{
		luckModifier += value;
	}
	
	public void setDropItems(boolean value)
	{
		dropItems = value;
	}
	
	public int getRolls()
	{
		return lootRolls;
	}
	
	public boolean shouldDropItems()
	{
		return dropItems;
	}
	
	public double getChance(float baseValue)
	{
		baseValue += luckPoints;
		baseValue *= luckModifier;
		return baseValue;
	}
	
	public NBTTagCompound getData()
	{
		return customData.copy();
	}
	
	public void setBoolean(String id, boolean value)
	{
		customData.setBoolean(id, value);
	}
	
	public void setByte(String id, byte value)
	{
		customData.setByte(id, value);
	}
	
	public void setShort(String id, short value)
	{
		customData.setShort(id, value);
	}
	
	public void setInteger(String id, int value)
	{
		customData.setInteger(id, value);
	}
	
	public void setLong(String id, long value)
	{
		customData.setLong(id, value);
	}
	
	public void setFloat(String id, float value)
	{
		customData.setFloat(id, value);
	}
	
	public void setDouble(String id, double value)
	{
		customData.setDouble(id, value);
	}
	
	public void setString(String id, String value)
	{
		customData.setString(id, value);
	}
	
	public void setTag(String id, NBTBase value)
	{
		customData.setTag(id, value.copy());
	}
}
