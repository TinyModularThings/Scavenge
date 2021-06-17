package speiger.src.scavenge.api.value;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

public class StringValue extends BaseValue
{
	Set<String> values;
	String defaultValue;
	boolean onlyValue;
	Predicate<String> tester;
	
	public StringValue(String name, String defaultValue, String...values)
	{
		super(name, defaultValue == null ? JsonNull.INSTANCE :new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new ObjectLinkedOpenHashSet<>(values);
	}
	
	public StringValue(String name, String defaultValue, ObjectCollection<String> values)
	{
		super(name, defaultValue == null ? JsonNull.INSTANCE :new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new ObjectLinkedOpenHashSet<>(values);
	}
	
	public StringValue(String name, String defaultValue, Iterator<String> values)
	{
		super(name, defaultValue == null ? JsonNull.INSTANCE :new JsonPrimitive(defaultValue));
		this.defaultValue = defaultValue;
		this.values = new ObjectLinkedOpenHashSet<>(values);
	}
	
	public StringValue setValueOnly()
	{
		onlyValue = true;
		return this;
	}
	
	public StringValue setTester(Predicate<String> tester)
	{
		this.tester = tester;
		return this;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.STRING;
	}
	
	@Override
	public List<String> getValues()
	{
		return new ObjectArrayList<>(values);
	}
	
	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		try
		{
			if(onlyValue) return values.contains(value);
			return tester == null || tester.test(value);
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
