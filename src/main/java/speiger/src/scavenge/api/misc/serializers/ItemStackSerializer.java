package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.RegistryValue;
import speiger.src.scavenge.api.value.StringValue;

public class ItemStackSerializer extends BaseSerializer<ItemStack>
{
	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();
		ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(obj.get("item").getAsString())), obj.get("count").getAsInt());
		if(obj.has("nbt")) stack.setTag(JsonUtils.toNBTCompound(obj.get("nbt")));
		return stack;
	}
	
	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("item", src.getItem().getRegistryName().toString());
		if(src.getTag() != null)
		{
			obj.add("nbt", JsonUtils.compoundToJson(src.getTag()));
		}
		obj.addProperty("count", src.getCount());
		return obj;
	}
	
	public static List<ItemStack> deserialze(PacketBuffer buffer)
	{
		List<ItemStack> list = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			list.add(buffer.readItem());
		}
		return list;
	}
	
	public static void serialze(List<ItemStack> list, PacketBuffer buffer)
	{
		buffer.writeVarInt(list.size());
		for(int i = 0,m=list.size();i<m;i++)
		{
			buffer.writeItem(list.get(i));
		}
	}
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new RegistryValue<>("item", ForgeRegistries.ITEMS, Items.DIAMOND).setDescription("Item that should be used"));
		value.addChild(new StringValue("nbt", null).setOptional(true).setDescription("NBT data in the item"));
		value.addChild(new IntValue("count", 0).setDescription("Amount of items"));
		return value;
	}
}
