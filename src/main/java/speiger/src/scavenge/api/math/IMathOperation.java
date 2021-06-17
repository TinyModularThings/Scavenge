package speiger.src.scavenge.api.math;

import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.StringValue;

public interface IMathOperation
{
	public long modify(long value);
	
	public static IValue createExampleValue() { return createExampleValue(""); }
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new StringValue("operation", "scavenge:add").setDescription("The Operation that should be executed"));
		value.addChild(new IntValue("value", 1).setDescription("Value that should be added"));
		return value;
	}
}
