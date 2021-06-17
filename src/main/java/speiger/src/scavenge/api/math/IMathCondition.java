package speiger.src.scavenge.api.math;

import speiger.src.scavenge.api.value.ArrayValue;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.StringValue;

public interface IMathCondition
{
	public boolean matches(long value);
	
	public static IValue createExampleValue() { return createExampleValue(""); }
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new StringValue("condition", "scavenge:always_true").setDescription("Value that should be checked against"));
		value.addChild(new ArrayValue("operations").addChild(IMathOperation.createExampleValue()).setDescription("Operations that should be applied before the test"));
		return value;
	}
}
