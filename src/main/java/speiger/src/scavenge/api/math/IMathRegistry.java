package speiger.src.scavenge.api.math;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.misc.JsonUtils;

public interface IMathRegistry
{
	public void registerMathCondition(Class<? extends IMathCondition> operation, ResourceLocation location, IScavengeBuilder<? extends IMathCondition> builder);
	public void registerMathOperation(Class<? extends IMathOperation> operation, ResourceLocation location, IScavengeBuilder<? extends IMathOperation> builder);
	
	public JsonObject serializeMathCondition(IMathCondition condition);
	public IMathCondition deserializeMathCondition(JsonObject object);
	
	public default JsonElement getConditionObject(JsonObject obj)
	{
		JsonElement el = obj.get("condition");
		return el.isJsonPrimitive() ? obj : el;
	}
	
	public default IMathCondition deserializeMathConditions(JsonElement element)
	{
		List<IMathCondition> result = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			IMathCondition condition = deserializeMathCondition(T);
			if(condition != null) result.add(condition);
		});
		return result.size() == 0 ? AlwaysTrueCondition.INSTANCE : (result.size() == 1 ? result.get(0) : new ArrayMathCondition(result));
	}
	
	public void serializeMathCondition(IMathCondition condition, PacketBuffer buffer);
	public IMathCondition deserializeMathCondition(PacketBuffer buffer);
	
	public JsonObject serializeMathOperation(IMathOperation operation);
	public IMathOperation deserializeMathOperation(JsonObject object);
	
	public default JsonArray serializeMathOperations(List<IMathOperation> properties)
	{
		JsonArray array = new JsonArray();
		for(int i = 0;i<properties.size();i++)
		{
			array.add(serializeMathOperation(properties.get(i)));
		}
		return array;
	}
	
	public default List<IMathOperation> deserializeMathOperations(JsonElement element)
	{
		List<IMathOperation> properties = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			IMathOperation property = deserializeMathOperation(T);
			if(property != null) properties.add(property);
		});
		return properties;
	}
	
	public void serializeMathOperation(IMathOperation operation, PacketBuffer buffer);
	public IMathOperation deserializeMathOperation(PacketBuffer buffer);
	
	public default void serializeMathOperations(List<IMathOperation> properties, PacketBuffer buffer)
	{
		buffer.writeVarInt(properties.size());
		for(int i = 0;i<properties.size();i++)
		{
			serializeMathOperation(properties.get(i), buffer);
		}
	}
	
	public default List<IMathOperation> deserializeMathOperations(PacketBuffer buffer)
	{
		List<IMathOperation> properties = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			IMathOperation property = deserializeMathOperation(buffer);
			if(property != null) properties.add(property);
		}
		return properties;
	}
}
