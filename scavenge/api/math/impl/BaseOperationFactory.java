package scavenge.api.math.impl;

import java.util.function.BiConsumer;

import scavenge.api.IScavengeFactory;
import scavenge.api.autodoc.Elements;
import scavenge.api.autodoc.MapElement;
import scavenge.api.autodoc.OptionalArrayElement;
import scavenge.api.math.IMathOperation;
import scavenge.api.utils.CompatState;

public abstract class BaseOperationFactory implements IScavengeFactory<IMathOperation>
{
	String id;
	CompatState state;
	
	public BaseOperationFactory(String id)
	{
		this.id = id;
	}
	
	public void setCompatState(CompatState state)
	{
		this.state = state;
	}

	@Override
	public String getID()
	{
		return id;
	}
	
	@Override
	public void addIncompats(BiConsumer<String, CompatState> states)
	{
		if(state != null)
		{
			states.accept(getID(), state);
		}
	}
	
	@Override
	public MapElement getDocumentation()
	{
		MapElement map = new MapElement("");
		map.addElement(new OptionalArrayElement("modifiers", new MapElement("").addElement(Elements.MATH_MODS.copyWithID("type"))));
		return map;
	}
}
