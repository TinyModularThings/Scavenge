package scavenge.api.autodoc;

public class RegistryElement extends BaseElement<String>
{
	
	public RegistryElement(String id)
	{
		super(id);
		setType("Registry Element");
	}
	
	public RegistryElement copyWithID(String id)
	{
		RegistryElement element = new RegistryElement(id);
		element.setDefaultValue(getDefaultValue());
		element.setDescription(getDescription());
		return element;
	}

	@Override
	public boolean isMultiElement()
	{
		return true;
	}
	
	@Override
	public boolean hasDefaultValue()
	{
		return false;
	}

	@Override
	public String getValue()
	{
		return "";
	}

	@Override
	public String toString()
	{
		return "Registry[id="+key+"]";
	}
}
