package scavenge.api.autodoc;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class ArrayElement extends BaseElement<String>
{
	List<BaseElement> elements = new ArrayList<BaseElement>();
	Class<? extends BaseElement> elementType;
	
	public ArrayElement(String id)
	{
		super(id);
		setType("MixedArray");
		elementType = null;
	}
	
	public ArrayElement(String id, BaseElement base)
	{
		super(id);
		setType(base.getType()+"Array");
		elementType = base.getClass();
	}
	
	public ArrayElement addElement(BaseElement element)
	{
		if(elementType == null || elementType.isInstance(element))
		{
			elements.add(element);
		}
		return this;
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
		super.writeToBuffer(writer, showSelf, layer, printCurse);
		if(elementType == null)
		{
			for(BaseElement element : elements)
			{
				element.writeToBuffer(writer, false, layer + 1, printCurse);
			}
		}
		else
		{
			if(elements.size() > 0)
			{
				BaseElement element = elements.get(0);
				if(element instanceof MapElement || element instanceof ChoiceElement || element instanceof ArrayElement)
				{
					element.writeToBuffer(writer, false, layer + 1, printCurse);
				}
			}
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i<elements.size();i++)
		{
			builder.append(elements.get(i));
			if(elements.size() - 1 > i)
			{
				builder.append(", ");
			}
		}
		return "Array[ID="+key+",value=<"+builder.toString()+">]";
	}
}
