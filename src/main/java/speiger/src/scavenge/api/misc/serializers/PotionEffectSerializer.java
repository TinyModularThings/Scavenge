package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.BooleanValue;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.RegistryValue;

public class PotionEffectSerializer extends BaseSerializer<EffectInstance>
{
	@Override
	public EffectInstance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();
		Effect effect = JsonUtils.deserializeEntry(obj.get("effect"), ForgeRegistries.POTIONS);
		if(effect == null) return null;
		int duration = obj.get("duration").getAsInt();
		int amplifier = JsonUtils.getOrDefault(obj, "amplifier", 0);
		boolean visible = JsonUtils.getOrDefault(obj, "particles", true);
		return new EffectInstance(effect, duration, amplifier, false, visible);
	}
	
	@Override
	public JsonElement serialize(EffectInstance src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("effect", src.getEffect().getRegistryName().toString());
		obj.addProperty("duration", src.getDuration());
		obj.addProperty("amplifier", src.getAmplifier());
		obj.addProperty("particles", src.isVisible());
		return obj;
	}
	
	public static IValue createExampleValue() { return createExampleValue(""); }
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new RegistryValue<>("effect", ForgeRegistries.POTIONS, Effects.LUCK).setDescription("The Effect that should be applied"));
		value.addChild(new IntValue("duration", 0).setDescription("Duration of the Effect"));
		value.addChild(new IntValue("amplifier", 0).setOptional(true).setDescription("The Amplifier of the Effect"));
		value.addChild(new BooleanValue("particles").setOptional(true).setDescription("If Particles should be visible"));
		return value;
	}
	
	public static void serialize(EffectInstance effect, PacketBuffer buffer)
	{
		buffer.writeRegistryIdUnsafe(ForgeRegistries.POTIONS, effect.getEffect());
		buffer.writeVarInt(effect.getDuration());
		buffer.writeVarInt(effect.getAmplifier());
		buffer.writeByte((effect.isAmbient() ? 1 : 0) | (effect.isVisible() ? 2 : 0) | (effect.showIcon() ? 4 : 0));
	}
	
	public static EffectInstance deserialize(PacketBuffer buffer)
	{
		Effect effect = buffer.readRegistryIdUnsafe(ForgeRegistries.POTIONS);
		int duration = buffer.readVarInt();
		int amplifier = buffer.readVarInt();
		int flags = buffer.readByte();
		return new EffectInstance(effect, duration, amplifier, (flags & 1) != 0, (flags & 2) != 0, (flags & 4) != 0);
	}
}
