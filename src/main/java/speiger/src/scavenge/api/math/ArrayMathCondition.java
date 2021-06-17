package speiger.src.scavenge.api.math;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.PacketBuffer;
import speiger.src.scavenge.api.ScavengeRegistry;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.ArrayValue;
import speiger.src.scavenge.api.value.IValue;

public class ArrayMathCondition extends BaseMathCondition
{
	List<IMathCondition> conditions;
	
	public ArrayMathCondition(List<IMathCondition> conditions)
	{
		this.conditions = conditions;
	}
	
	@Override
	public boolean matches(long value)
	{
		value = getValue(value);
		for(int i = 0,m=conditions.size();i<m;i++)
		{
			if(!conditions.get(i).matches(value)) return false;
		}
		return true;
	}
	
	public static class Builder extends BaseBuilder<ArrayMathCondition>
	{
		@Override
		public ArrayMathCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			List<IMathCondition> conditions = new ObjectArrayList<>();
			JsonUtils.iterate(json.getAsJsonObject().get("conditions"), T -> {
				IMathCondition condition = ScavengeRegistry.INSTANCE.deserializeMathCondition(T);
				if(condition != null) conditions.add(condition);
			});
			return deserializeOperations(json.getAsJsonObject(), new ArrayMathCondition(conditions));
		}
		
		@Override
		public JsonElement serialize(ArrayMathCondition src, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonArray array = new JsonArray();
			for(int i = 0;i<src.conditions.size();i++)
			{
				JsonObject obj = ScavengeRegistry.INSTANCE.serializeMathCondition(src.conditions.get(i));
				if(obj != null) array.add(obj);
			}
			JsonObject obj = new JsonObject();
			obj.add("conditions", obj);
			return serializeOperations(obj, src);
		}
		
		@Override
		public void addDefaultValues(Consumer<IValue> values)
		{
			values.accept(new ArrayValue("conditions").addChild(IMathCondition.createExampleValue()).setDescription("Array of Math Conditions"));
			super.addOperations(values);
		}
		
		@Override
		public String getDescription()
		{
			return "Array condtion to tests for a desired value";
		}
		
		@Override
		public ArrayMathCondition deserialize(PacketBuffer buffer)
		{
			List<IMathCondition> conditions = new ObjectArrayList<>();
			int size = buffer.readVarInt();
			for(int i = 0;i<size;i++)
			{
				IMathCondition condition = ScavengeRegistry.INSTANCE.deserializeMathCondition(buffer);
				if(condition != null) conditions.add(condition);
			}
			return new ArrayMathCondition(conditions);
		}
		
		@Override
		public void serialize(ArrayMathCondition value, PacketBuffer buffer)
		{
			buffer.writeVarInt(value.conditions.size());
			for(int i = 0,m=value.conditions.size();i<m;i++)
			{
				ScavengeRegistry.INSTANCE.serializeMathCondition(value.conditions.get(i), buffer);
			}
		}
	}
}