package speiger.src.scavenge.api.value;

import java.util.Iterator;
import java.util.List;
import java.util.function.DoublePredicate;

import com.google.gson.JsonPrimitive;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleLinkedOpenHashSet;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class DoubleValue extends BaseValue
{
	DoubleSet values;
	double defaultValue;
	boolean onlyValue;
	DoublePredicate tester;
	
	public DoubleValue(String name, double defaultValue, double...values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new DoubleLinkedOpenHashSet(values);
	}
	
	public DoubleValue(String name, double defaultValue, DoubleCollection values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new DoubleLinkedOpenHashSet(values);
	}
	
	public DoubleValue(String name, double defaultValue, Iterator<Double> values)
	{
		super(name, new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new DoubleLinkedOpenHashSet(values);
	}
	
	public DoubleValue setValueOnly()
	{
		onlyValue = true;
		return this;
	}
	
	public DoubleValue setTester(DoublePredicate tester)
	{
		this.tester = tester;
		return this;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.DOUBLE;
	}
	
	@Override
	public List<String> getValues()
	{
		List<String> list = new ObjectArrayList<>();
		for(double entry : values)
		{
			list.add(Double.toString(entry));
		}
		return list;
	}
	
	@Override
	public String getDefaultValue()
	{
		return Double.toString(defaultValue);
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		try
		{
			double number = Double.parseDouble(value);
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
