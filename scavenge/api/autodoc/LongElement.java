package scavenge.api.autodoc;

public class LongElement extends BaseElement<Long>
{
	long value;
	
	public LongElement(String id, long value, String desc)
	{
		super(id);
		this.value = value;
		setType("LongNumber");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public LongElement(String id, long theValue)
	{
		super(id);
		setType("LongNumber");
		value = theValue;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}

	@Override
	public Long getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return "Long[ID="+key+",value="+value+"]";
	}
}
