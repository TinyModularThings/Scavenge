package scavenge.api.autodoc;

public class FloatElement extends BaseElement<Float>
{
	float value;
	
	public FloatElement(String id, float value, String desc)
	{
		super(id);
		this.value = value;
		setType("FloatNumber");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public FloatElement(String id, float theValue)
	{
		super(id);
		setType("FloatNumber");
		value = theValue;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}
	
	@Override
	public Float getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "Float[ID="+key+",value="+value+"]";
	}
}
