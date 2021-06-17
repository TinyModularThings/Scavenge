package speiger.src.scavenge.api.properties;

import java.util.Map;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class DataContainer
{
	Map<String, JsonObject> data = new Object2ObjectOpenHashMap<>();
	float luck = 0F;
	
	public void addLuck(float value)
	{
		this.luck += value;
	}
	
	public void addData(String key, JsonObject value)
	{
		data.put(key, value);
	}
	
	public JsonObject getData(String id)
	{
		return data.getOrDefault(id, new JsonObject());
	}
	
	public Map<String, JsonObject> getData()
	{
		return data;
	}

	public float getLuck()
	{
		return luck;
	}
}