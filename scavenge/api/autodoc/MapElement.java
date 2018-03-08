package scavenge.api.autodoc;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapElement extends BaseElement<String>
{
	Map<String, BaseElement> elementMap = new LinkedHashMap<String, BaseElement>();
	boolean isDocumentation = false;
	
	public MapElement(String id)
	{
		super(id);
		setType("Object");
	}
	
	public MapElement addElement(BaseElement element)
	{
		elementMap.put(element.getKey(), element);
		return this;
	}
	
	public void setDocumentationState(boolean value)
	{
		isDocumentation = value;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}

	@Override
	public String getValue()
	{
		return "";
	}
	
	@Override
	public void writeToBuffer(BufferedWriter writer, boolean showSelf, int layer, boolean printCurse) throws Exception
	{
		if(printCurse)
		{
			if(isDocumentation)
			{
				writer.write(getOffset(layer)+"<strong>Description</strong>: "+getDescription());
				writer.write("<br />");
				writer.newLine();
				if(elementMap.size() > 0)
				{
					writer.write(getOffset(layer)+"<strong>Parameters</strong>:");
					writer.write("<br />");
					writer.newLine();
				}
			}
			if(showSelf)
			{
				writer.write(getOffset(layer) + "<strong>\"" + key + "\"</strong>: (" + getDescription() + ") " + getType() + ",");
				writer.write("<br />");
				writer.newLine();
			}
		}
		else
		{
			if(isDocumentation)
			{
				writer.write(getOffset(layer)+"Description: "+getDescription());
				writer.newLine();
				if(elementMap.size() > 0)
				{
					writer.write(getOffset(layer)+"Parameters:");
					writer.newLine();
				}
			}
			if(showSelf)
			{
				writer.write(getOffset(layer) + "\"" + key + "\": (" + getDescription() + ") " + getType() + ",");
				writer.newLine();
			}
		}
		for(BaseElement el : elementMap.values())
		{
			el.writeToBuffer(writer, true, layer+1, printCurse);
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		Iterator<BaseElement> iter = elementMap.values().iterator();
		while(iter.hasNext())
		{
			builder.append(iter.next().toString());
			if(iter.hasNext())
			{
				builder.append(", ");
			}
		}
		return "Map[id="+key+",value=("+builder.toString()+")]";
	}
}
