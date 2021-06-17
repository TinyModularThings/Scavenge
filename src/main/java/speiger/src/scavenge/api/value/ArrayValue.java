package speiger.src.scavenge.api.value;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class ArrayValue extends BaseValue
{
	List<IValue> children = new ObjectArrayList<>();
	
	public ArrayValue(String name)
	{
		super(name, new JsonArray());
	}
	
	public ArrayValue(String name, JsonArray array)
	{
		super(name, array);
	}
	
	public ArrayValue addChild(IValue value)
	{
		children.add(value);
		return this;
	}
	
	public ArrayValue addChildren(IValue...values)
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
	public List<IValue> getChildNodes()
	{
		return children;
	}
	
	@Override
	public List<String> getValues()
	{
		return Arrays.asList("[]");
	}
	
	@Override
	public String getDefaultValue()
	{
		return "[]";
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		return value.equalsIgnoreCase("[]");
	}
}