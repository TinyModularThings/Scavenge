package scavenge.api.autodoc;

import java.io.BufferedWriter;

public abstract class BaseElement<T>
{
	String key;
	String description = "";
	T defaultValue;
	boolean hasDefaultValue = false;
	String type;
	
	public BaseElement(String id)
	{
		key = id;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public BaseElement setDefaultValue(T value)
	{
		defaultValue = value;
		hasDefaultValue = value != null;
		return this;
	}
	
	public BaseElement setDescription(String description)
	{
		this.description = description;
		return this;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public T getDefaultValue()
	{
		return defaultValue;
	}
	
	public String getType()
	{
		return type;
	}
	
	public BaseElement setType(String type)
	{
		this.type = type;
		return this;
	}
	
	public boolean hasDefaultValue()
	{
		return hasDefaultValue;
	}
	
	public abstract T getValue();
	
	public abstract boolean isMultiElement();
	
	public void writeToBuffer(BufferedWriter writer, boolean showSelf, int layer, boolean printCurse) throws Exception
	{
		if(printCurse)
		{
			if(hasDefaultValue())
			{
				writer.write(getOffset(layer) + "<strong>\"" + key + "\"</strong>: (" + getDescription() + ", Default: " + getDefaultValue().toString() + ") " + getType() + ",");
			}
			else
			{
				writer.write(getOffset(layer) + "<strong>\"" + key + "\"</strong>: (" + getDescription() + ") " + getType() + ",");
			}
			writer.write("<br />");
		}
		else
		{
			if(hasDefaultValue())
			{
				writer.write(getOffset(layer) + "\"" + key + "\": (" + getDescription() + ", Default: " + getDefaultValue().toString() + ") " + getType() + ",");
			}
			else
			{
				writer.write(getOffset(layer) + "\"" + key + "\": (" + getDescription() + ") " + getType() + ",");
			}
		}
		writer.newLine();
	}
	
	protected String getOffset(int layer)
	{
		if(layer <= 0)
		{
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i < layer;i++)
		{
			builder.append("   ");
		}
		return builder.toString();
	}
	
	@Override
	public abstract String toString();
}
