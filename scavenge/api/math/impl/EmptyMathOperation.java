package scavenge.api.math.impl;

import java.util.function.BiConsumer;

import com.google.gson.JsonObject;

import scavenge.api.IScavengeFactory;
import scavenge.api.autodoc.BooleanElement;
import scavenge.api.autodoc.MapElement;
import scavenge.api.math.IMathOperation;
import scavenge.api.utils.CompatState;

public class EmptyMathOperation implements IMathOperation
{
	boolean result;
	
	public EmptyMathOperation(boolean defaultValue)
	{
		result = defaultValue;
	}
	
	@Override
	public boolean matches(long value)
	{
		return result;
	}
	
	@Override
	public String getID()
	{
		return "default";
	}
	
	public static class EmptyOperationFactory implements IScavengeFactory<IMathOperation>
	{
		@Override
		public IMathOperation createObject(JsonObject obj)
		{
			return new EmptyMathOperation(obj.has("value") ? obj.get("value").getAsBoolean() : true);
		}

		@Override
		public String getID()
		{
			return "default";
		}
		
		@Override
		public void addIncompats(BiConsumer<String, CompatState> states)
		{
			
		}
		
		@Override
		public MapElement getDocumentation()
		{
			MapElement element = new MapElement("");
			element.setDescription("A Default MathOperation that just returns true or false");
			element.addElement(new BooleanElement("value", true).setDescription("Which value should be returned"));
			return element;
		}
		
		@Override
		public void addExample(JsonObject obj)
		{
			obj.addProperty("value", true);
		}
	}
	
}
