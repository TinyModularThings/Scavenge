
package scavenge.api.autodoc;

public class TextElement extends BaseElement<String>
{
	String value;
	
	public TextElement(String id, String value, String desc)
	{
		super(id);
		this.value = value;
		setType("String");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public TextElement(String id, String theValue)
	{
		super(id);
		setType("String");
		value = theValue;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}

	@Override
	public String getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "Text[ID="+key+",value="+value+"]";
	}
}
