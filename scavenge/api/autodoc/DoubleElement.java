package scavenge.api.autodoc;

public class DoubleElement extends BaseElement<Double>
{
	double value;
	
	public DoubleElement(String id, double value, String desc)
	{
		super(id);
		this.value = value;
		setType("FloatNumber");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public DoubleElement(String id, double theValue)
	{
		super(id);
		setDescription("DoubleNumber");
		value = theValue;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}
	
	@Override
	public Double getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "Double[ID="+key+",value="+value+"]";
	}
}
