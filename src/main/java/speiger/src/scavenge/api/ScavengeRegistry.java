package speiger.src.scavenge.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.math.IMathCondition;
import speiger.src.scavenge.api.math.IMathOperation;
import speiger.src.scavenge.api.math.IMathRegistry;
import speiger.src.scavenge.api.misc.SimpleRegistry;
import speiger.src.scavenge.api.misc.serializers.BlockPosSerializer;
import speiger.src.scavenge.api.misc.serializers.BlockStateSerializer;
import speiger.src.scavenge.api.misc.serializers.FluidStackSerializer;
import speiger.src.scavenge.api.misc.serializers.ItemStackSerializer;
import speiger.src.scavenge.api.misc.serializers.PotionEffectSerializer;
import speiger.src.scavenge.api.misc.serializers.StackObj;
import speiger.src.scavenge.api.properties.IPropertyRegistry;
import speiger.src.scavenge.api.properties.IScavengeProperty;
import speiger.src.scavenge.api.storage.IWorldRegistry;

public class ScavengeRegistry implements IPropertyRegistry, IMathRegistry
{
	public static Function<Boolean, IWorldRegistry> WORLD_REGISTRY;
	public static final ScavengeRegistry INSTANCE = new ScavengeRegistry();
	SimpleRegistry<IScavengeProperty> properties = new SimpleRegistry<>();
	SimpleRegistry<IMathCondition> conditions = new SimpleRegistry<>();
	SimpleRegistry<IMathOperation> operations = new SimpleRegistry<>();
	Map<ToolType, List<ItemStack>> toolToItems = new Object2ObjectLinkedOpenHashMap<>();
	Map<ResourceLocation, Set<ResourceLocation>> inCompats = new Object2ObjectLinkedOpenHashMap<>();
	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson;
	
	public static IWorldRegistry getRegistry()
	{
		return getRegistry(EffectiveSide.get().isServer());
	}
	
	public static IWorldRegistry getRegistry(boolean server)
	{
		return WORLD_REGISTRY.apply(server);
	}
	
	@Override
	public void registerProperty(Class<? extends IScavengeProperty> clz, ResourceLocation id, IScavengeBuilder<? extends IScavengeProperty> builder)
	{
		properties.register(clz, id, builder);
		gsonBuilder.registerTypeAdapter(clz, builder);
	}
	
	@Override
	public void registerMathCondition(Class<? extends IMathCondition> clz, ResourceLocation id, IScavengeBuilder<? extends IMathCondition> builder)
	{
		conditions.register(clz, id, builder);
		gsonBuilder.registerTypeAdapter(clz, builder);
	}
	
	@Override
	public void registerMathOperation(Class<? extends IMathOperation> clz, ResourceLocation id, IScavengeBuilder<? extends IMathOperation> builder)
	{
		operations.register(clz, id, builder);
		gsonBuilder.registerTypeAdapter(clz, builder);
	}
	
	public boolean isIncompatible(Class<? extends IScavengeProperty> main, Class<? extends IScavengeProperty> other)
	{
		return inCompats.getOrDefault(properties.getId(main), ObjectSets.emptySet()).contains(properties.getId(other));
	}
	
	public Set<ResourceLocation> getIncompats(ResourceLocation id)
	{
		return inCompats.getOrDefault(id, ObjectSets.emptySet());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void registerIncompat(Class<? extends IScavengeProperty> clz, Class<? extends IScavengeProperty>... incompats)
	{
		List<ResourceLocation> ids = new ObjectArrayList<>();
		for(Class<? extends IScavengeProperty> prop : incompats)
		{
			ResourceLocation id = properties.getId(prop);
			if(id != null) ids.add(id);
		}
		registerIncompat(properties.getId(clz), ids.toArray(new ResourceLocation[ids.size()]));
	}

	@Override
	public void registerIncompat(Class<? extends IScavengeProperty> clz, ResourceLocation... incompats)
	{
		registerIncompat(properties.getId(clz), incompats);
	}
	
	@Override
	public void registerIncompat(ResourceLocation type, ResourceLocation... incompats)
	{
		if(type == null) return;
		Set<ResourceLocation> location = inCompats.get(type);
		if(location == null)
		{
			location = new ObjectLinkedOpenHashSet<>();
			inCompats.put(type, location);
		}
		location.addAll(ObjectArrayList.wrap(incompats));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void registerIncompat(ResourceLocation type, Class<? extends IScavengeProperty>... incompats)
	{
		List<ResourceLocation> ids = new ObjectArrayList<>();
		for(Class<? extends IScavengeProperty> prop : incompats)
		{
			ResourceLocation id = properties.getId(prop);
			if(id != null) ids.add(id);
		}
		registerIncompat(type, ids.toArray(new ResourceLocation[ids.size()]));
	}

	public void init()
	{
		gsonBuilder.registerTypeAdapter(StackObj.class, new StackObj.Serializer());
		gsonBuilder.registerTypeAdapter(EffectInstance.class, new PotionEffectSerializer());
		gsonBuilder.registerTypeAdapter(BlockPos.class, new BlockPosSerializer());
		gsonBuilder.registerTypeAdapter(BlockState.class, new BlockStateSerializer());
		gsonBuilder.registerTypeAdapter(FluidStack.class, new FluidStackSerializer());
		gsonBuilder.registerTypeAdapter(ItemStack.class, new ItemStackSerializer());
		gson = gsonBuilder.create();
	}
	
	public Map<ResourceLocation, IScavengeBuilder<?>> getBuilder()
	{
		Map<ResourceLocation, IScavengeBuilder<?>> builder = new Object2ObjectLinkedOpenHashMap<>();
		builder.putAll(properties.getIdToBuilder());
		builder.putAll(conditions.getIdToBuilder());
		builder.putAll(operations.getIdToBuilder());
		return builder;
	}
	
	public ResourceLocation getBuilderId(IScavengeBuilder<?> builder)
	{
		return properties.getId(builder);
	}
	
	public List<ItemStack> getItemsForTool(ToolType type)
	{
		List<ItemStack> list = toolToItems.get(type);
		if(list == null)
		{
			list = new ObjectArrayList<>();
			for(Item item : ForgeRegistries.ITEMS) {
				if(item.getToolTypes(new ItemStack(item)).contains(type)) {
					list.add(new ItemStack(item));					
				}
			}
			toolToItems.put(type, list);
		}
		return list;
	}
	
	@Override
	public JsonObject serializeProperty(IScavengeProperty property)
	{
		JsonObject obj = gson.toJsonTree(property).getAsJsonObject();
		obj.addProperty("property", properties.getId(property.getClass()).toString());
		return obj;
	}
	
	@Override
	public IScavengeProperty deserializeProperty(JsonObject object)
	{
		Class<? extends IScavengeProperty> property = properties.getClass(ResourceLocation.tryParse(object.get("property").getAsString()));
		if(property == null) return null;
		return gson.fromJson(object, property);
	}

	@Override
	public void serializeProperty(IScavengeProperty property, PacketBuffer buffer)
	{
		ResourceLocation location = properties.getId(property.getClass());
		buffer.writeResourceLocation(location);
		properties.getBuilder(location).serialize(property, buffer);
	}

	@Override
	public IScavengeProperty deserializeProperty(PacketBuffer buffer)
	{
		IScavengeBuilder<IScavengeProperty> builder = properties.getBuilder(buffer.readResourceLocation());
		return builder == null ? null : builder.deserialize(buffer);
	}

	@Override
	public JsonObject serializeMathCondition(IMathCondition condition)
	{
		JsonObject obj = gson.toJsonTree(condition).getAsJsonObject();
		obj.addProperty("condition", conditions.getId(condition.getClass()).toString());
		return obj;
	}

	@Override
	public IMathCondition deserializeMathCondition(JsonObject object)
	{
		Class<? extends IMathCondition> property = conditions.getClass(ResourceLocation.tryParse(object.get("condition").getAsString()));
		if(property == null) return null;
		return gson.fromJson(object, property);
	}

	@Override
	public void serializeMathCondition(IMathCondition condition, PacketBuffer buffer)
	{
		ResourceLocation location = conditions.getId(condition.getClass());
		buffer.writeResourceLocation(location);
		conditions.getBuilder(location).serialize(condition, buffer);
	}

	@Override
	public IMathCondition deserializeMathCondition(PacketBuffer buffer)
	{
		IScavengeBuilder<IMathCondition> builder = conditions.getBuilder(buffer.readResourceLocation());
		return builder == null ? null : builder.deserialize(buffer);
	}

	@Override
	public JsonObject serializeMathOperation(IMathOperation operation)
	{
		JsonObject obj = gson.toJsonTree(operation).getAsJsonObject();
		obj.addProperty("operation", operations.getId(operation.getClass()).toString());
		return obj;
	}

	@Override
	public IMathOperation deserializeMathOperation(JsonObject object)
	{
		Class<? extends IMathOperation> property = operations.getClass(ResourceLocation.tryParse(object.get("operation").getAsString()));
		if(property == null) return null;
		return gson.fromJson(object, property);
	}

	@Override
	public void serializeMathOperation(IMathOperation operation, PacketBuffer buffer)
	{
		ResourceLocation location = operations.getId(operation.getClass());
		buffer.writeResourceLocation(location);
		operations.getBuilder(location).serialize(operation, buffer);
	}

	@Override
	public IMathOperation deserializeMathOperation(PacketBuffer buffer)
	{
		IScavengeBuilder<IMathOperation> builder = operations.getBuilder(buffer.readResourceLocation());
		return builder == null ? null : builder.deserialize(buffer);
	}
}