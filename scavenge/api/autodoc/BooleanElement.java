package scavenge.api.autodoc;

public class BooleanElement extends BaseElement<Boolean>
{
	boolean value;
	
	public BooleanElement(String id, boolean value, String desc)
	{
		super(id);
		this.value = value;
		setType("Boolean");
		setDefaultValue(value);
		setDescription(desc);
	}
	
	public BooleanElement(String id, boolean value)
	{
		super(id);
		this.value = value;
		setType("Boolean");
	}
	
	@Override
	public Boolean getValue()
	{
		return value;
	}
	
	@Override
	public boolean isMultiElement()
	{
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Boolean[ID="+key+",value="+value+"]";
	}
	
}
