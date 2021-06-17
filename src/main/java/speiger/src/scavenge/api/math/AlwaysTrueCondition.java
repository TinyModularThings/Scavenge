package speiger.src.scavenge.api.math;

import java.lang.reflect.Type;
import java.util.function.Consumer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.network.PacketBuffer;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.value.IValue;

public class AlwaysTrueCondition implements IMathCondition
{
	public static final IMathCondition INSTANCE = new AlwaysTrueCondition();
	
	@Override
	public boolean matches(long value)
	{
		return true;
	}
	
	public static class Builder implements IScavengeBuilder<AlwaysTrueCondition>
	{
		@Override
		public AlwaysTrueCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new AlwaysTrueCondition();
		}
		
		@Override
		public JsonElement serialize(AlwaysTrueCondition src, Type typeOfSrc, JsonSerializationContext context)
		{
			return new JsonObject();
		}
		
		@Override
		public String getDescription()
		{
			return "Math Operation that always returns true";
		}
		
		@Override
		public OperationType getType()
		{
			return OperationType.MATH_CONDITION;
		}
		
		@Override
		public void addDefaultValues(Consumer<IValue> values) {}

		@Override
		public AlwaysTrueCondition deserialize(PacketBuffer buffer)
		{
			return new AlwaysTrueCondition();
		}
		
		@Override
		public void serialize(AlwaysTrueCondition value, PacketBuffer buffer) {}
	}
}
