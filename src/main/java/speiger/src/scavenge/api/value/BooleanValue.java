package speiger.src.scavenge.api.value;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonPrimitive;

public class BooleanValue extends BaseValue
{
	boolean defaultValue;
	
	public BooleanValue(String name)
	{
		this(name, true);
	}
	
	public BooleanValue(String name, boolean defaultValue)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
	}
	
	@Override
	public boolean onlyUsesValues()
	{
		return true;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.BOOLEAN;
	}
	
	@Override
	public List<String> getValues()
	{
		return Arrays.asList("true", "false");
	}

	@Override
	public boolean isValueValid(String value)
	{
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
	}

	@Override
	public String getDefaultValue()
	{
		return Boolean.toString(defaultValue);
	}
}