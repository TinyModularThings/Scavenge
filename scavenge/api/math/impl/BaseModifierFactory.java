package scavenge.api.math.impl;

import java.util.function.BiConsumer;

import com.google.gson.JsonObject;

import scavenge.api.IScavengeFactory;
import scavenge.api.autodoc.MapElement;
import scavenge.api.math.IMathModifier;
import scavenge.api.utils.CompatState;

public abstract class BaseModifierFactory implements IScavengeFactory<IMathModifier>
{
	String id;
	
	public BaseModifierFactory(String id)
	{
		this.id = id;
	}
	
	@Override
	public String getID()
	{
		return id;
	}

	@Override
	public void addIncompats(BiConsumer<String, CompatState> states)
	{
		
	}
}
