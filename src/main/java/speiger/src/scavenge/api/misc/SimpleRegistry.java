package speiger.src.scavenge.api.misc;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;
import speiger.src.scavenge.api.IScavengeBuilder;

public class SimpleRegistry<T>
{
	Object2ObjectMap<Class<? extends T>, ResourceLocation> clzToId = new Object2ObjectOpenHashMap<>();
	Object2ObjectMap<ResourceLocation, IScavengeBuilder<T>> idToBuilder = new Object2ObjectOpenHashMap<>();
	Object2ObjectMap<IScavengeBuilder<?>, ResourceLocation> builderToId = new Object2ObjectOpenHashMap<>();
	Object2ObjectMap<ResourceLocation, Class<? extends T>> idToClz = new Object2ObjectOpenHashMap<>();
	
	@SuppressWarnings("unchecked")
	public void register(Class<? extends T> clz, ResourceLocation id, IScavengeBuilder<? extends T> builder)
	{
		clzToId.put(clz, id);
		idToBuilder.put(id, (IScavengeBuilder<T>)builder);
		builderToId.put(builder, id);
		idToClz.put(id, clz);
	}
	
	public ResourceLocation getId(Class<? extends T> clz)
	{
		return clzToId.get(clz);
	}
	
	public ResourceLocation getId(IScavengeBuilder<?> id)
	{
		return builderToId.get(id);
	}
	
	public IScavengeBuilder<T> getBuilder(ResourceLocation id)
	{
		return idToBuilder.get(id);
	}
	
	public Class<? extends T> getClass(ResourceLocation id)
	{
		return idToClz.get(id);
	}
	
	public Object2ObjectMap<ResourceLocation, IScavengeBuilder<T>> getIdToBuilder()
	{
		return idToBuilder;
	}
}
