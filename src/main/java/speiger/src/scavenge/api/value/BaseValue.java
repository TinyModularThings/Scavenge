package speiger.src.scavenge.api.value;

import com.google.gson.JsonElement;

public abstract class BaseValue implements IValue
{
	String name;
	String description;
	JsonElement exampleValue;
	boolean optional;
	
	public BaseValue(String name, JsonElement exampleValue)
	{
		this.name = name;
		this.exampleValue = exampleValue;
	}
	
	public BaseValue setDescription(String description)
	{
		this.description = description;
		return this;
	}
	
	public BaseValue setOptional(boolean optional)
	{
		this.optional = optional;
		return this;
	}
	
	@Override
	public boolean isOptional()
	{
		return optional;
	}
	
	@Override
	public JsonElement getExampleValue()
	{
		return exampleValue;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getDescription()
	{
		return description;
	}
}
