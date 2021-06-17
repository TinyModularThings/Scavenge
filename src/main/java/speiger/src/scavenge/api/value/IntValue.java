package speiger.src.scavenge.api.value;

import java.util.Iterator;
import java.util.List;
import java.util.function.IntPredicate;

import com.google.gson.JsonPrimitive;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class IntValue extends BaseValue
{
	IntSet values;
	int defaultValue;
	boolean onlyValue;
	IntPredicate tester;
	
	public IntValue(String name, int defaultValue, int...values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new IntLinkedOpenHashSet(values);
	}
	
	public IntValue(String name, int defaultValue, IntCollection values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new IntLinkedOpenHashSet(values);
	}
	
	public IntValue(String name, int defaultValue, Iterator<Integer> values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new IntLinkedOpenHashSet(values);
	}
	
	public IntValue setValueOnly()
	{
		onlyValue = true;
		return this;
	}
	
	public IntValue setTester(IntPredicate tester)
	{
		this.tester = tester;
		return this;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.INTEGER;
	}
	
	@Override
	public List<String> getValues()
	{
		List<String> list = new ObjectArrayList<>();
		for(int entry : values)
		{
			list.add(Integer.toString(entry));
		}
		return list;
	}
	
	@Override
	public String getDefaultValue()
	{
		return Integer.toString(defaultValue);
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		try
		{
			int number = Integer.parseInt(value);
			if(onlyValue) return values.contains(number);
			return tester == null || tester.test(number);
		}
		catch(Exception e)
		{
		}
		return false;
	}

	@Override
	public boolean onlyUsesValues()
	{
		return onlyValue;
	}
}
