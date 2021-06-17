package speiger.src.scavenge.api.value;

import java.util.List;

import com.google.gson.JsonPrimitive;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryValue<T extends ForgeRegistryEntry<T>> extends BaseValue
{
	IForgeRegistry<T> registry;
	T defaultValue;
	
	public RegistryValue(String name, IForgeRegistry<T> registry)
	{
		this(name, registry, registry.getDefaultKey());
	}
	
	public RegistryValue(String name, IForgeRegistry<T> registry, ResourceLocation defaultValue)
	{
		this(name, registry, registry.getValue(defaultValue));
	}
	
	public RegistryValue(String name, IForgeRegistry<T> registry, T defaultValue)
	{
		super(name, new JsonPrimitive(defaultValue == null ? "" : defaultValue.getRegistryName().toString()));
		this.registry = registry;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public ValueType getType()
	{
		return ValueType.REGISTRY;
	}
	
	@Override
	public boolean onlyUsesValues()
	{
		return true;
	}
	
	@Override
	public List<String> getValues()
	{
		List<String> values = new ObjectArrayList<>();
		for(ResourceLocation entry : registry.getKeys()) values.add(entry.toString());
		return values;
	}
	
	@Override
	public String getDefaultValue()
	{
		return defaultValue == null ? "" : defaultValue.toString();
	}
	
	@Override
	public boolean isValueValid(String value)
	{
		return registry.containsKey(ResourceLocation.tryParse(value));
	}
	
}
