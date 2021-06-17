package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.RegistryValue;
import speiger.src.scavenge.api.value.StringValue;

public class FluidStackSerializer extends BaseSerializer<FluidStack>
{
	@Override
	public FluidStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();
		Fluid fluid = JsonUtils.deserializeEntry(obj.get("fluid"), ForgeRegistries.FLUIDS);
		if(fluid == null) return null;
		return new FluidStack(fluid, obj.get("amount").getAsInt(), obj.has("nbt") ? JsonUtils.toNBTCompound(obj.get("nbt")) : null);
	}
	
	@Override
	public JsonElement serialize(FluidStack src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.add("fluid", JsonUtils.serializeEntry(src.getFluid()));
		obj.addProperty("amount", src.getAmount());
		if(src.hasTag()) obj.add("nbt", JsonUtils.toJson(src.getTag()));
		return obj;
	}
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new RegistryValue<>("fluid", ForgeRegistries.FLUIDS, Fluids.WATER).setDescription("The Desired Fluid"));
		value.addChild(new IntValue("amount", 0).setDescription("The Amount of the Desired Fluid"));
		value.addChild(new StringValue("nbt", "").setOptional(true).setDescription("The NBTData of the Fluid"));
		return value;
	}
}