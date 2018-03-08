package scavenge.api.autodoc;

public class Elements
{
	//MC
	public static final RegistryElement ITEM = createElement("RegistryName for the Required Item", "minecraft:diamond");
	public static final RegistryElement BLOCK = createElement("RegistryName for the Required Block", "minecraft:dirt");
	
	//Scavenge Math
	public static final RegistryElement MATH_MODS = createElement("RegistryName for the Math Modifier", "default");
	public static final RegistryElement MATH_OPS = createElement("RegistryName for the Math Operation", "default");
	//Scavenge API
	public static final RegistryElement RESOURCE_PROPERTY = createElement("RegistryName for the Block Property", "");
	public static final RegistryElement LOOT_PROPERTY = createElement("RegistryName for the Loot Property", "");
	
	
	public static final RegistryElement createElement(String description, String defaultValue)
	{
		RegistryElement el = new RegistryElement("");
		el.setDescription(description);
		if(defaultValue.length() > 0)
		{
			el.setDefaultValue(defaultValue);
		}
		return el;
	}
}
