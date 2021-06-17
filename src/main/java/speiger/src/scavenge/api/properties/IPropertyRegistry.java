package speiger.src.scavenge.api.properties;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.misc.JsonUtils;

public interface IPropertyRegistry
{
	@SuppressWarnings("unchecked")
	public void registerIncompat(Class<? extends IScavengeProperty> clz, Class<? extends IScavengeProperty>...incompats);
	public void registerIncompat(Class<? extends IScavengeProperty> clz, ResourceLocation...incompats);
	public void registerIncompat(ResourceLocation type, ResourceLocation...incompats);
	@SuppressWarnings("unchecked")
	public void registerIncompat(ResourceLocation type, Class<? extends IScavengeProperty>...incompats);

	public void registerProperty(Class<? extends IScavengeProperty> clz, ResourceLocation id, IScavengeBuilder<? extends IScavengeProperty> builder);
	
	public JsonObject serializeProperty(IScavengeProperty property);
	public IScavengeProperty deserializeProperty(JsonObject object);
	
	public default IScavengeCondition deserializeCondition(JsonObject object)
	{
		IScavengeProperty property = deserializeProperty(object);
		return property instanceof IScavengeCondition ? (IScavengeCondition)property : null;
	}
	
	public default IScavengeEffect deserializeEffect(JsonObject object)
	{
		IScavengeProperty property = deserializeProperty(object);
		return property instanceof IScavengeEffect ? (IScavengeEffect)property : null;
	}
	
	public default JsonArray serializeProperties(List<? extends IScavengeProperty> properties)
	{
		JsonArray array = new JsonArray();
		for(int i = 0;i<properties.size();i++)
		{
			array.add(serializeProperty(properties.get(i)));
		}
		return array;
	}
	
	public default List<IScavengeProperty> deserializeProperties(JsonElement element)
	{
		List<IScavengeProperty> properties = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			IScavengeProperty property = deserializeProperty(T);
			if(property != null) properties.add(property);
		});
		return properties;
	}
	
	public default List<IScavengeCondition> deserializeConditions(JsonElement element)
	{
		List<IScavengeCondition> properties = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			IScavengeCondition property = deserializeCondition(T);
			if(property != null) properties.add(property);
		});
		return properties;
	}
	
	public default List<IScavengeEffect> deserializeEffects(JsonElement element)
	{
		List<IScavengeEffect> properties = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			IScavengeEffect property = deserializeEffect(T);
			if(property != null) properties.add(property);
		});
		return properties;
	}
	
	public void serializeProperty(IScavengeProperty property, PacketBuffer buffer);
	public IScavengeProperty deserializeProperty(PacketBuffer buffer);
	
	public default IScavengeCondition deserializeCondition(PacketBuffer buffer)
	{
		IScavengeProperty property = deserializeProperty(buffer);
		return property instanceof IScavengeCondition ? (IScavengeCondition)property : null;
	}
	
	public default IScavengeEffect deserializeEffect(PacketBuffer buffer)
	{
		IScavengeProperty property = deserializeProperty(buffer);
		return property instanceof IScavengeEffect ? (IScavengeEffect)property : null;
	}
	
	public default void serializeProperties(List<? extends IScavengeProperty> properties, PacketBuffer buffer)
	{
		buffer.writeVarInt(properties.size());
		for(int i = 0;i<properties.size();i++)
		{
			serializeProperty(properties.get(i), buffer);
		}
	}
	
	public default List<IScavengeProperty> deserializeProperties(PacketBuffer buffer)
	{
		List<IScavengeProperty> properties = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			IScavengeProperty property = deserializeProperty(buffer);
			if(property != null) properties.add(property);
		}
		return properties;
	}
	
	public default List<IScavengeCondition> deserializeConditions(PacketBuffer buffer)
	{
		List<IScavengeCondition> properties = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			IScavengeCondition property = deserializeCondition(buffer);
			if(property != null) properties.add(property);
		}
		return properties;
	}
	
	public default List<IScavengeEffect> deserializeEffects(PacketBuffer buffer)
	{
		List<IScavengeEffect> properties = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			IScavengeEffect property = deserializeEffect(buffer);
			if(property != null) properties.add(property);
		}
		return properties;
	}
}
