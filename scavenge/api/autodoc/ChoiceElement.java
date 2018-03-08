package scavenge.api.autodoc;

import java.io.BufferedWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChoiceElement extends BaseElement<String>
{
	Map<String, BaseElement>[] elements;
	
	public ChoiceElement(String id, int choices)
	{
		super(id);
		elements = new Map[choices];
		for(int i = 0;i<choices;i++)
		{
			elements[i] = new LinkedHashMap<String, BaseElement>();
		}
		setType("ChoiceType");
	}
	
	public void addElement(int index, BaseElement element)
	{
		elements[index].put(element.getKey(), element);
	}
	
	@Override
	public boolean isMultiElement()
	{
		return true;
	}

	@Override
	public String getValue()
	{
		return "";
	}
	
	@Override
	public void writeToBuffer(BufferedWriter writer, boolean showSelf, int layer, boolean printCurse) throws Exception
	{
		if(elements.length == 0 || elements.length == 1)
		{
			return;
		}
		for(int i = 0;i<elements.length;i++)
		{
			if(printCurse)
			{
				writer.write(getOffset(layer) + (i == 0 ? "either" : "or"));
				writer.write("<br />");
			}
			else
			{
				writer.write(getOffset(layer) + (i == 0 ? "either" : "or"));
			}
			writer.newLine();
			for(BaseElement element : elements[i].values())
			{
				element.writeToBuffer(writer, false, layer+1, printCurse);
			}
		}
	}
	
	@Override
	public String toString()
	{
		return "Choice[ID="+key+"]";
	}
}
