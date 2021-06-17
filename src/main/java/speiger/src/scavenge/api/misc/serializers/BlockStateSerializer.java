package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.misc.MiscUtil;
import speiger.src.scavenge.api.value.ArrayValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.RegistryValue;
import speiger.src.scavenge.api.value.StringValue;

public class BlockStateSerializer extends BaseSerializer<BlockState>
{

	@Override
	public BlockState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();
		Block block = JsonUtils.deserializeEntry(obj.get("block"), ForgeRegistries.BLOCKS);
		if(block != null)
		{
			AtomicReference<BlockState> stateWrapper = new AtomicReference<>(block.defaultBlockState());
			JsonUtils.iterate(obj.get("properties"), V -> stateWrapper.set(MiscUtil.buildProperty(stateWrapper.get(), V.get("property").getAsString(), V.get("value").getAsString())));
			return stateWrapper.get();
		}
		return null;
	}

	@Override
	public JsonElement serialize(BlockState src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonArray properties = new JsonArray();
		for(Property<?> property : src.getBlock().getStateDefinition().getProperties())
		{
			JsonObject prop = new JsonObject();
			prop.addProperty("property", property.getName());
			prop.addProperty("value", MiscUtil.getPropertyValue(src, property.getName()));
			properties.add(prop);
		}
		JsonObject block = new JsonObject();
		block.add("block", JsonUtils.serializeEntry(src.getBlock()));
		block.add("properties", properties);
		return block;
	}
	
	public static ObjectValue generateExampleValue(String name)
	{
		ObjectValue block = new ObjectValue(name);
		block.addChild(new ArrayValue("properties").addChild(new ObjectValue("property")
				.addChild(new StringValue("property", "snowy").setDescription("Property that should be set"))
				.addChild(new StringValue("value", "true").setDescription("The Value"))).setOptional(true).setDescription("The Properties that should be applied on the block"));
		block.addChild(new RegistryValue<>("block", ForgeRegistries.BLOCKS, Blocks.DIAMOND_BLOCK).setDescription("The Block that should be used"));
		return block;
	}
}
