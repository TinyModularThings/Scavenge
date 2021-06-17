package speiger.src.scavenge.api.value;

import java.util.List;
import java.util.function.Function;

import com.google.gson.JsonPrimitive;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class EnumValue<T extends Enum<T>> extends BaseValue
{
	Class<T> clz;
	T defaultValue;
	Function<T, String> mapper;
	
	public EnumValue(String name, Class<T> clz, T defaultValue, Function<T, String> mapper)
	{
		super(name, new JsonPrimitive(mapper.apply(defaultValue)));
		this.clz = clz;
		this.defaultValue = defaultValue;
		this.mapper = mapper;
	}

	@Override
	public ValueType getType()
	{
		return ValueType.ENUM;
	}
	
	@Override
	public boolean onlyUsesValues()
	{
		return true;
	}
	
	@Override
	public List<String> getValues()
	{
		List<String> values = new ObjectArrayList<>();
		for(T value : clz.getEnumConstants())
		{
			values.add(mapper.apply(value));
		}
		return values;
	}
	
	@Override
	public String getDefaultValue()
	{
		return mapper.apply(defaultValue);
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		try
		{
			return Enum.valueOf(clz, value) != null;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
}
