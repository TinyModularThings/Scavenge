package speiger.src.scavenge.api.math;

import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.StringValue;

public interface IMathOperation
{
	public long modify(long value);
	
	public static ObjectValue createExampleValue() { return createExampleValue(""); }
	
	public static ObjectValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new StringValue("operation", "scavenge:add").setDescription("The Operation that should be executed"));
		value.addChild(new IntValue("value", 1).setDescription("Value that should be added"));
		return value;
	}
}
