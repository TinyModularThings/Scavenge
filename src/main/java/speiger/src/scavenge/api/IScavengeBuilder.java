package speiger.src.scavenge.api;

import java.util.function.Consumer;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import net.minecraft.network.PacketBuffer;
import speiger.src.scavenge.api.value.IValue;

public interface IScavengeBuilder<T> extends JsonDeserializer<T>, JsonSerializer<T>
{
	public void addDefaultValues(Consumer<IValue> values);
	public String getDescription();
	public T deserialize(PacketBuffer buffer);
	public void serialize(T value, PacketBuffer buffer);
	
	public OperationType getType();
	
	public static enum OperationType
	{
		CONDITION,
		EFFECT,
		CONDITIONAL_EFFECT,
		MATH_OPERATION,
		MATH_CONDITION,
		STRUCTURE_PROCESSOR;
	}
}
