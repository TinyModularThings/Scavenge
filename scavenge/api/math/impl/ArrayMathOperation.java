package scavenge.api.math.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import scavenge.api.IScavengeFactory;
import scavenge.api.ScavengeAPI;
import scavenge.api.autodoc.Elements;
import scavenge.api.autodoc.MapElement;
import scavenge.api.autodoc.OptionalArrayElement;
import scavenge.api.math.IMathOperation;
import scavenge.api.utils.CompatState;
import scavenge.api.utils.JsonUtil;

public class ArrayMathOperation implements IMathOperation
{
	List<IMathOperation> operations = new ArrayList<IMathOperation>();
	
	public ArrayMathOperation()
	{
	}
	
	public void addOperation(IMathOperation operation)
	{
		operations.add(operation);
	}
	
	@Override
	public boolean matches(long value)
	{
		for(IMathOperation op : operations)
		{
			if(!op.matches(value))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String getID()
	{
		return "array";
	}
	
	public static class ArrayFactory implements IScavengeFactory<IMathOperation>
	{
		@Override
		public IMathOperation createObject(JsonObject obj)
		{
			final ArrayMathOperation operation = new ArrayMathOperation();
			if(obj.has("operations"))
			{
				JsonUtil.convertToObject(obj, new Consumer<JsonObject>(){
					@Override
					public void accept(JsonObject t)
					{
						IMathOperation op = ScavengeAPI.INSTANCE.getMathOperation(t.get("type").getAsString(), t);
						if(op != null)
						{
							operation.addOperation(op);
						}
					}
				});
			}
			return operation;
		}

		@Override
		public String getID()
		{
			return "array";
		}
		
		@Override
		public void addIncompats(BiConsumer<String, CompatState> states)
		{
		}
		
		@Override
		public MapElement getDocumentation()
		{
			MapElement element = new MapElement("");
			element.addElement(new OptionalArrayElement("operations", new MapElement("").addElement(Elements.MATH_OPS.copyWithID("type"))));
			element.setDescription("Allows to combine Math Operations together.");
			return element;
		}
		
		@Override
		public void addExample(JsonObject obj)
		{
			JsonArray array = new JsonArray();
			array.add(new JsonObject());
			obj.add("operations", array);
		}
	}
}
