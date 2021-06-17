package speiger.src.scavenge.api.misc.serializers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.IntValue;
import speiger.src.scavenge.api.value.ObjectValue;
import speiger.src.scavenge.api.value.RegistryValue;
import speiger.src.scavenge.api.value.StringValue;

public class StackObj
{
	ItemStack stack;
	int amount;
	
	public StackObj(ItemStack stack, int amount)
	{
		this.stack = stack;
		this.amount = amount;
	}
	
	public StackObj(PacketBuffer buffer)
	{
		stack = buffer.readItem();
		amount = buffer.readInt();
	}
	
	public void serialize(PacketBuffer buffer)
	{
		buffer.writeItem(stack);
		buffer.writeInt(amount);
	}
	
	public ITextComponent getName()
	{
		return stack.getHoverName();
	}
	
	public ITextComponent getAmountName()
	{
		return new StringTextComponent(amount+"x ").append(stack.getHoverName());
	}
	
	public static List<StackObj> deserialze(PacketBuffer buffer)
	{
		List<StackObj> list = new ObjectArrayList<>();
		int size = buffer.readVarInt();
		for(int i = 0;i<size;i++)
		{
			list.add(new StackObj(buffer));
		}
		return list;
	}
	
	public static void serialze(List<StackObj> list, PacketBuffer buffer)
	{
		buffer.writeVarInt(list.size());
		for(int i = 0,m=list.size();i<m;i++)
		{
			list.get(i).serialize(buffer);
		}
	}
	
	public static IValue createExampleValue() { return createExampleValue(""); }
	
	public static IValue createExampleValue(String name)
	{
		ObjectValue value = new ObjectValue(name);
		value.addChild(new RegistryValue<>("item", ForgeRegistries.ITEMS, Items.DIAMOND).setDescription("Item that should be used"));
		value.addChild(new StringValue("nbt", null).setOptional(true).setDescription("NBT data in the item"));
		value.addChild(new IntValue("count", 0).setOptional(true).setDescription("Amount of items"));
		return value;
	}
	
	public boolean matches(ItemStack other)
	{
		return ItemStack.isSame(stack, other) && NBTUtil.compareNbt(stack.getTag(), other.getTag(), false);
	}
	
	public Item getItem()
	{
		return stack.getItem();
	}
	
	public ItemStack getStack()
	{
		return stack;
	}
	
	public ItemStack getAmountStack()
	{
		ItemStack copy = stack.copy();
		copy.setCount(getAmount());
		return copy;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public static class Serializer implements JsonDeserializer<StackObj>, JsonSerializer<StackObj>
	{
		@Override
		public JsonElement serialize(StackObj src, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonObject obj = new JsonObject();
			obj.addProperty("item", src.stack.getItem().getRegistryName().toString());
			if(src.stack.getTag() != null)
			{
				obj.add("nbt", JsonUtils.compoundToJson(src.stack.getTag()));
			}
			obj.addProperty("count", src.amount);
			return obj;
		}

		@Override
		public StackObj deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			JsonObject obj = json.getAsJsonObject();
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(obj.get("item").getAsString())));
			if(obj.has("nbt")) stack.setTag(JsonUtils.toNBTCompound(obj.get("nbt")));
			return new StackObj(stack, JsonUtils.getOrDefault(obj, "count", 0));
		}
	}
}
