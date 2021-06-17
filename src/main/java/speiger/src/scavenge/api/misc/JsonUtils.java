package speiger.src.scavenge.api.misc;

import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.EndNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class JsonUtils
{
	public static void iterate(JsonElement element, Consumer<JsonObject> objects)
	{
		if(element.isJsonObject()) objects.accept(element.getAsJsonObject());
		else if(element.isJsonArray()) element.getAsJsonArray().forEach(T -> iterate(T, objects));
	}
	
	public static JsonObject getOrSelf(JsonObject obj, String tag)
	{
		return obj.has(tag) ? obj.getAsJsonObject(tag) : obj;
	}
	
	public static long getOrDefault(JsonObject obj, String tag, long defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsLong() : defaultValue;
	}
	
	public static int getOrDefault(JsonObject obj, String tag, int defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsInt() : defaultValue;
	}
	
	public static float getOrDefault(JsonObject obj, String tag, float defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsFloat() : defaultValue;
	}
	
	public static double getOrDefault(JsonObject obj, String tag, double defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsDouble() : defaultValue;
	}
	
	public static boolean getOrDefault(JsonObject obj, String tag, boolean defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsBoolean() : defaultValue;
	}
	
	public static String getOrDefault(JsonObject obj, String tag, String defaultValue)
	{
		return obj.has(tag) ? obj.get(tag).getAsString() : defaultValue;
	}
	
	public static <V extends ForgeRegistryEntry<V>> V deserializeEntry(JsonElement element, IForgeRegistry<V> registry)
	{
		if(!element.isJsonPrimitive()) return null;
		ResourceLocation location = ResourceLocation.tryParse(element.getAsString());
		return location != null ? registry.getValue(location) : null;
	}
	
	public static <V extends ForgeRegistryEntry<V>> JsonPrimitive serializeEntry(V entry)
	{
		return new JsonPrimitive(entry.getRegistryName().toString());
	}
	
	public static <T> List<T> deserialze(JsonElement element, Class<T> clz, JsonDeserializationContext context)
	{
		List<T> list = new ObjectArrayList<>();
		iterate(element, V -> {
			T value = context.deserialize(V, clz);
			if(value != null) list.add(value);
		});
		return list;
	}
	
	public static <T> JsonArray serialize(List<T> list, JsonSerializationContext context)
	{
		JsonArray array = new JsonArray();
		for(T entry : list) array.add(context.serialize(entry));
		return array;
	}
	
	public static CompoundNBT toNBTCompound(JsonElement element)
	{
		try
		{
			return JsonToNBT.parseTag(element.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonElement toJson(INBT nbt)
	{
		if(nbt instanceof EndNBT) return JsonNull.INSTANCE;
		if(nbt instanceof CompoundNBT) return compoundToJson((CompoundNBT)nbt);
		if(nbt instanceof CollectionNBT) return arrayToJson((CollectionNBT<?>)nbt);
		if(nbt instanceof NumberNBT || nbt instanceof StringNBT) return valueToJson(nbt);
		return null;
	}
	
	public static JsonObject compoundToJson(CompoundNBT nbt)
	{
		JsonObject obj = new JsonObject();
		for(String key : nbt.getAllKeys())
		{
			obj.add(key, toJson(nbt.get(key)));
		}
		return obj;
	}
	
	public static JsonArray listToJson(ListNBT list)
	{
		JsonArray array = new JsonArray();
		for(int i = 0;i<list.size();i++)
		{
			array.add(toJson(list.get(i)));
		}
		return array;
	}
	
	public static JsonArray arrayToJson(CollectionNBT<?> nbt)
	{
		if(nbt instanceof ListNBT) return listToJson((ListNBT)nbt);
		if(nbt instanceof ByteArrayNBT)
		{
			JsonArray array = new JsonArray();
			for(byte value : ((ByteArrayNBT)nbt).getAsByteArray()) 
			{
				array.add(value);
			}
			return array;
		}
		else if(nbt instanceof IntArrayNBT)
		{
			JsonArray array = new JsonArray();
			for(int value : ((IntArrayNBT)nbt).getAsIntArray()) 
			{
				array.add(value);
			}
			return array;
		}
		else if(nbt instanceof LongArrayNBT)
		{
			JsonArray array = new JsonArray();
			for(long value : ((LongArrayNBT)nbt).getAsLongArray()) 
			{
				array.add(value);
			}
			return array;
		}
		return null;
	}
	
	public static JsonPrimitive valueToJson(INBT nbt)
	{
		if(nbt instanceof StringNBT) return new JsonPrimitive(nbt.getAsString());
		if(nbt instanceof NumberNBT)
		{
			if(nbt instanceof ByteNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsByte());
			if(nbt instanceof ShortNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsShort());
			if(nbt instanceof IntNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsInt());
			if(nbt instanceof LongNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsLong());
			if(nbt instanceof FloatNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsFloat());
			if(nbt instanceof DoubleNBT) return new JsonPrimitive(((NumberNBT)nbt).getAsDouble());
		}
		return null;
	}
}