package scavenge.api.autodoc;

import java.io.BufferedWriter;

public class OptionalArrayElement extends BaseElement<String>
{
	MapElement map;
	
	public OptionalArrayElement(String id, BaseElement type, MapElement base)
	{
		super(id);
		map = base;
		setType("Optional"+type.getType()+"Array");
		setDescription("It can be either a "+type.getType()+" or a "+type.getType()+"Array");
	}
	
	public OptionalArrayElement(String id, MapElement base)
	{
		super(id);
		map = base;
		setType("OptionalObjectArray");
		setDescription("It can be either a Object or a ObjectArray");
	}

	@Override
	public String getValue()
	{
		return "";
	}

	@Override
	public boolean isMultiElement()
	{
		return true;
	}
	
	@Override
	public BaseElement setDescription(String desc)
	{
		if(description == null || description.isEmpty())
		{
			return super.setDescription(desc);
		}
		return super.setDescription(desc+" ("+description+")");
	}
	
	@Override
	public void writeToBuffer(BufferedWriter writer, boolean showSelf, int layer, boolean printCurse) throws Exception
	{
		super.writeToBuffer(writer, showSelf, layer, printCurse);
		map.writeToBuffer(writer, false, layer, printCurse);
	}
	
	@Override
	public String toString()
	{
		return "Choice[ID="+key+"]";
	}
}
