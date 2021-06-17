package speiger.src.scavenge.api.value;

import java.util.List;

import com.google.gson.JsonElement;

import it.unimi.dsi.fastutil.objects.ObjectLists;

public interface IValue
{
	public IValue setDescription(String s);
	public String getName();
	public String getDescription();
	public ValueType getType();
	public boolean isOptional();
	public boolean onlyUsesValues();
	public List<String> getValues();
	public String getDefaultValue();
	public JsonElement getExampleValue();
	public boolean isValueValid(String value);
	public default List<IValue> getChildNodes() { return ObjectLists.emptyList(); }
	
	public static enum ValueType
	{
		BOOLEAN,
		INTEGER,
		DOUBLE,
		STRING,
		ENUM,
		REGISTRY,
		OBJECT,
		ARRAY;
	}
}
