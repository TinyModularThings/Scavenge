package scavenge.api.loot.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import scavenge.api.loot.ILootFactory;
import scavenge.api.utils.CompatState;

public abstract class BaseLootFactory implements ILootFactory
{
	String id;
	Map<String, CompatState> map = new LinkedHashMap<String, CompatState>();
	boolean active;
	
	public BaseLootFactory(String id, boolean active)
	{
		this.id = id;
		this.active = active;
	}
	
	public void setCompatState(CompatState state)
	{
		map.put(getID(), state);
	}
	
	public void addIncompat(String id, CompatState state)
	{
		map.put(id, state);
	}
	
	public void addIncompats(CompatState state, String... ids)
	{
		for(String id : ids)
		{
			map.put(id, state);
		}
	}
	
	@Override
	public String getID()
	{
		return id;
	}
		
	@Override
	public void addIncompats(BiConsumer<String, CompatState> states)
	{
		if(map.size() > 0)
		{
			map.forEach(states);
		}
	}
	
	@Override
	public boolean isActiveProperty()
	{
		return active;
	}
	
}
