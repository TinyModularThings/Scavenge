package scavenge.api.autodoc;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonConverter
{
	
	public static MapElement createFromJson(JsonObject element)
	{
		MapElement base = new MapElement("");
		for(Entry<String, JsonElement> entry : element.entrySet())
		{
			BaseElement el = createObject(entry.getKey(), entry.getValue());
			if(el != null)
			{
				return base.addElement(el);
			}
		}
		return base;
	}
	
	public static BaseElement createObject(String id, JsonElement element)
	{
		if(element.isJsonArray())
		{
			ArrayElement array = new ArrayElement(id);
			for(JsonElement el : element.getAsJsonArray())
			{
				BaseElement base = createObject("", el);
				if(base != null)
				{
					array.addElement(base);
				}
			}
			return array;
		}
		else if(element.isJsonObject())
		{
			JsonObject obj = element.getAsJsonObject();
			MapElement map = new MapElement(id);
			for(Entry<String, JsonElement> entry : obj.entrySet())
			{
				BaseElement base = createObject(entry.getKey(), entry.getValue());
				if(base != null)
				{
					map.addElement(base);
				}
			}
			return map;
		}
		else if(element.isJsonNull())
		{
			return null;
		}
		else
		{
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if(primitive.isBoolean())
			{
				return new BooleanElement(id, primitive.getAsBoolean());
			}
			else if(primitive.isNumber())
			{
				double d = primitive.getAsDouble();
				if(Math.floor(d) != d)
				{
					return new LongElement(id, primitive.getAsLong());
				}
				return new DoubleElement(id, d);
			}
			else if(primitive.isString())
			{
				return new TextElement(id, primitive.getAsString());
			}
			return null;
		}
	}
	
}
