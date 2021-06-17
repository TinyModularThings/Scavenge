package speiger.src.scavenge.api.value;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class ObjectValue extends BaseValue
{
	List<IValue> children = new ObjectArrayList<>();
	
	public ObjectValue(String name)
	{
		super(name, new JsonObject());
	}
	
	public ObjectValue(String name, JsonObject obj)
	{
		super(name, obj);
	}
	
	public ObjectValue addChild(IValue value)
	{
		children.add(value);
		return this;
	}
	
	public ObjectValue addChildren(IValue...values)
	{
		this.children.addAll(ObjectArrayList.wrap(values));
		return this;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.ARRAY;
	}
	
	@Override
	public boolean onlyUsesValues()
	{
		return true;
	}
	
	@Override
	public List<String> getValues()
	{
		return Arrays.asList("{}");
	}
	
	@Override
	public List<IValue> getChildNodes()
	{
		return children;
	}
	
	@Override
	public String getDefaultValue()
	{
		return "{}";
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		return value.equalsIgnoreCase("{}");
	}
}