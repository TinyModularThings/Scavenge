package scavenge.api.block.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import scavenge.api.autodoc.BooleanElement;
import scavenge.api.autodoc.ChoiceElement;
import scavenge.api.autodoc.Elements;
import scavenge.api.autodoc.MapElement;
import scavenge.api.autodoc.TextElement;
import scavenge.api.block.IResourceFactory;
import scavenge.api.utils.CompatState;

public abstract class BaseResourceFactory implements IResourceFactory
{
	String id;
	Map<String, CompatState> incompats = new HashMap<String, CompatState>();
	PropertyType type;
	
	public BaseResourceFactory(String id, PropertyType theType)
	{
		this.id = id;
		type = theType;
	}
	
	/**
	 * Function to add Duplication Incompats
	 * @param state
	 */
	public void setCompatState(CompatState state)
	{
		incompats.put(getID(), state);
	}
	
	/**
	 * Function for the other BlockProperties that it is incompatible to
	 * @param id the ID of the Entry thats incompatible
	 * @param state the incompat state
	 */
	public void addIncompat(String id, CompatState state)
	{
		incompats.put(id, state);
	}
	
	/**
	 * Multi Version from addIncompat
	 */
	public void addIncompats(CompatState state, String...ids)
	{
		for(String key : ids)
		{
			incompats.put(key, state);
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
		if(incompats.size() > 0)
		{
			incompats.forEach(states);
		}
	}
	
	@Override
	public MapElement getDocumentation()
	{
		MapElement element = new MapElement("");
		element.addElement(new BooleanElement("disableJEI", false, "Decides if the Condition is visible in JEI"));
		return element;
	}
	
	public void addDefaultOperationText(ChoiceElement element, int index)
	{
		element.addElement(index, Elements.MATH_OPS.copyWithID("operations"));
		element.addElement(index, new TextElement("jeiDesc", "").setDescription("The Description for the JEI plugin"));
	}
	
	@Override
	public PropertyType getType()
	{
		return type;
	}
	
}
