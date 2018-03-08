package scavenge.api.loot.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonObject;

import scavenge.api.ScavengeAPI;
import scavenge.api.loot.ILootProperty;

public abstract class BaseLootProperty implements ILootProperty
{
	String id;
	boolean active;
	Set<String> incompats = new HashSet<String>();
	
	public BaseLootProperty(String id, boolean active)
	{
		this.id = id;
		this.active = active;
	}
	
	@Override
	public String getID()
	{
		return id;
	}
	
	@Override
	public boolean canCombine(ILootProperty loot)
	{
		return !incompats.contains(loot.getID());
	}
	
	@Override
	public boolean isActiveProperty()
	{
		return active;
	}
	
	@Override
	public boolean hasMultiResults()
	{
		return active;
	}
	
	
	protected ILootProperty createLoot(JsonObject obj)
	{
		String id = obj.get("type").getAsString();
		ILootProperty prop = ScavengeAPI.INSTANCE.createLootProperty(id, obj);
		if(prop != null)
		{
			return prop;
		}
		throw new RuntimeException("Property ["+id+"] is null!");
	}
}
