package speiger.src.scavenge.api.math;

import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.PacketBuffer;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.ScavengeRegistry;
import speiger.src.scavenge.api.value.ArrayValue;
import speiger.src.scavenge.api.value.IValue;

public abstract class BaseMathCondition implements IMathCondition
{
	List<IMathOperation> operations = new ObjectArrayList<>();
	
	protected long getValue(long value)
	{
		for(int i = 0,m=operations.size();i<m;i++)
		{
			value = operations.get(i).modify(value);
		}
		return value;
	}
	
	public abstract static class BaseBuilder<T extends BaseMathCondition> implements IScavengeBuilder<T>
	{
		protected JsonObject serializeOperations(JsonObject obj, T src)
		{
			JsonArray operations = new JsonArray();
			for(int i = 0;i<src.operations.size();i++)
			{
				JsonObject subObj = ScavengeRegistry.INSTANCE.serializeMathOperation(src.operations.get(i));
				if(subObj != null) operations.add(subObj);
			}
			if(operations.size() > 0) obj.add("operations", operations.size() == 1 ? operations.get(0) : operations);
			return obj;
		}
		
		protected T deserializeOperations(JsonObject obj, T src)
		{
			if(obj.has("operations")) src.operations.addAll(ScavengeRegistry.INSTANCE.deserializeMathOperations(obj.get("operations")));
			return src;
		}
		
		@Override
		public OperationType getType()
		{
			return OperationType.MATH_CONDITION;
		}
		
		protected void addOperations(Consumer<IValue> builder)
		{
			builder.accept(new ArrayValue("operations").addChild(IMathOperation.createExampleValue()).setOptional(true).setDescription("Operations that should be applied before the test"));
		}
		
		protected void serializeOperations(T src, PacketBuffer buffer)
		{
			ScavengeRegistry.INSTANCE.serializeMathOperations(src.operations, buffer);
		}
		
		protected T deserializeOperations(T src, PacketBuffer buffer)
		{
			src.operations.addAll(ScavengeRegistry.INSTANCE.deserializeMathOperations(buffer));
			return src;
		}
	}
}
