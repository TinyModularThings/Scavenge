package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.math.BlockPos;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;

public class BlockPosSerializer extends BaseSerializer<BlockPos>
{
	@Override
	public BlockPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();
		return new BlockPos(obj.getAsJsonPrimitive("x").getAsInt(), obj.getAsJsonPrimitive("y").getAsInt(), obj.getAsJsonPrimitive("z").getAsInt());
	}
	
	@Override
	public JsonElement serialize(BlockPos src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("x", src.getX());
		obj.addProperty("y", src.getY());
		obj.addProperty("z", src.getZ());
		return obj;
	}
	
	public static IValue createExampleValue(String name, String description)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new IntValue("x", 0).setDescription("X "+description));
		value.addChild(new IntValue("y", 0).setDescription("Y "+description));
		value.addChild(new IntValue("z", 0).setDescription("Z "+description));
		return value;
	}
}