package scavenge.api.autodoc;

public class IntElement extends BaseElement<Integer>
{
	int value;
	
	public IntElement(String id, int value, String desc)
	{
		super(id);
		this.value = value;
		setType("IntNumber");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public IntElement(String id, int theValue)
	{
		super(id);
		setType("IntNumber");
		value = theValue;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}
	
	@Override
	public Integer getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "Integer[ID="+key+",value="+value+"]";
	}
}
