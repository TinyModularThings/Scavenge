package speiger.src.scavenge.api.misc.processors;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.misc.JsonUtils;

public interface IProcessorRegistry
{
	public void registerProcessor(Class<? extends StructureProcessor> cls, ResourceLocation id, IScavengeBuilder<? extends StructureProcessor> builder);
	
	public JsonObject serializeProcessor(StructureProcessor processor);
	public StructureProcessor deserializerProcessor(JsonObject object);
	
	public default JsonArray serializeProcessors(List<? extends StructureProcessor> processors) 
	{
		JsonArray array = new JsonArray();
		for(int i = 0;i<processors.size();i++)
		{
			array.add(serializeProcessor(processors.get(i)));
		}
		return array;
	}
	
	public default List<StructureProcessor> deserializerProcessors(JsonElement element)
	{
		List<StructureProcessor> properties = new ObjectArrayList<>();
		JsonUtils.iterate(element, T -> {
			StructureProcessor property = deserializerProcessor(T);
			if(property != null) properties.add(property);
		});
		return properties;
	}
	
	public void serializeProcessor(StructureProcessor processor, PacketBuffer buffer);
	public StructureProcessor deserializerProcessor(PacketBuffer buffer);
	
	public default void serializeProcessors(List<? extends StructureProcessor> processors, PacketBuffer buffer)
	{
		buffer.writeVarInt(processors.size());
		for(int i = 0;i<processors.size();i++)
		{
			serializeProcessor(processors.get(i), buffer);
		}
	}
	
	public default List<StructureProcessor> deserializerProcessors(PacketBuffer buffer)
	{
		List<StructureProcessor> processors = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			StructureProcessor property = deserializerProcessor(buffer);
			if(property != null) processors.add(property);
		}
		return processors;
	}
}
