package scavenge.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scavenge.api.ScavengeAPI;
import scavenge.api.math.IMathOperation;
import scavenge.api.math.impl.ArrayMathOperation;
import scavenge.api.math.impl.EmptyMathOperation;

public class JsonUtil
{	
	public static void convertToObject(JsonElement el, Consumer<JsonObject> receiver)
	{
		if(el == null)
		{
			return;
		}
		if(el.isJsonArray())
		{
			for(JsonElement element : el.getAsJsonArray())
			{
				if(element.isJsonObject())
				{
					receiver.accept(element.getAsJsonObject());
				}
			}
		}
		else if(el.isJsonObject())
		{
			receiver.accept(el.getAsJsonObject());
		}
	}
	
	public static void convertToPrimitive(JsonElement el, Consumer<JsonPrimitive> receiver)
	{
		if(el == null)
		{
			return;
		}
		if(el.isJsonArray())
		{
			for(JsonElement element : el.getAsJsonArray())
			{
				if(element.isJsonPrimitive())
				{
					receiver.accept(element.getAsJsonPrimitive());
				}
			}
		}
		else if(el.isJsonPrimitive())
		{
			receiver.accept(el.getAsJsonPrimitive());
		}
	}
	
	public static List<Integer> convertToInts(JsonElement el)
	{
		final List<Integer> list = new ArrayList<Integer>();
		convertToPrimitive(el, new Consumer<JsonPrimitive>(){
			@Override
			public void accept(JsonPrimitive t)
			{
				list.add(t.getAsInt());
			}
		});
		return list;
	}
	
	public static IMathOperation getMathOperation(String searchID, JsonObject obj)
	{
		return getMathOperation(searchID, obj, true);
	}
	
	public static IMathOperation getMathOperation(String searchID, JsonObject object, boolean defaultValue)
	{
		if(object.has(searchID))
		{
			JsonElement element = object.get(searchID);
			if(element.isJsonArray())
			{
				ArrayMathOperation operations = new ArrayMathOperation();
				for(JsonElement el : element.getAsJsonArray())
				{
					if(el.isJsonObject())
					{
						JsonObject obj = el.getAsJsonObject();
						IMathOperation op = ScavengeAPI.INSTANCE.getMathOperation(obj.get("type").getAsString(), obj);
						if(op != null)
						{
							operations.addOperation(op);
						}
					}
				}
				return operations;
			}
			else if(element.isJsonObject())
			{
				JsonObject obj = element.getAsJsonObject();
				IMathOperation op = ScavengeAPI.INSTANCE.getMathOperation(obj.get("type").getAsString(), obj);
				if(op != null)
				{
					return op;
				}
			}
			else if(element.isJsonPrimitive())
			{
				IMathOperation op = ScavengeAPI.INSTANCE.getMathOperation(element.getAsString(), object);
				if(op != null)
				{
					return op;
				}
			}
		}
		return new EmptyMathOperation(defaultValue);
	}
	
	public static boolean getOrDefault(JsonObject obj, String id, boolean defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsBoolean();
		}
		return defaultValue;
	}
	
	public static int getOrDefault(JsonObject obj, String id, int defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsInt();
		}
		return defaultValue;
	}
	
	public static long getOrDefault(JsonObject obj, String id, long defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsLong();
		}
		return defaultValue;
	}
	
	public static float getOrDefault(JsonObject obj, String id, float defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsFloat();
		}
		return defaultValue;
	}
	
	public static double getOrDefault(JsonObject obj, String id, double defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsDouble();
		}
		return defaultValue;
	}
	
	public static String getOrDefault(JsonObject obj, String id, String defaultValue)
	{
		if(obj.has(id))
		{
			return obj.get(id).getAsString();
		}
		return defaultValue;
	}
	
	public static PotionEffect createPotionEffect(JsonObject obj)
	{
		Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(obj.get("name").getAsString()));
		if(potion == null)
		{
			return null;
		}
		int duration = obj.get("duration").getAsInt();
		int amplifier = getOrDefault(obj, "amplifier", 0);
		boolean showParticals = getOrDefault(obj, "particals", true);
		return new PotionEffect(potion, duration, amplifier, false, showParticals);
	}
	
	public static StackObject createStackObject(JsonObject obj)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(obj.get("name").getAsString()));
		if(item == null)
		{
			return null;
		}
		int meta = obj.has("meta") ? obj.get("meta").getAsInt() : Short.MAX_VALUE;
		int size = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
		NBTTagCompound nbt = obj.has("nbt") ? ScavengeAPI.INSTANCE.getCompound(obj.get("nbt").getAsString()) : null;
		return new StackObject(item, meta, size, nbt);
	}
	
	public static ItemStack createStack(JsonObject obj)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(obj.get("name").getAsString()));
		if(item == null)
		{
			return null;
		}
		int meta = obj.has("meta") ? obj.get("meta").getAsInt() : 0;
		int size = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
		return new ItemStack(item, size, meta);
	}
	
	public static ItemStack createStackWithNBT(JsonObject obj)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(obj.get("name").getAsString()));
		if(item == null)
		{
			return null;
		}
		int meta = obj.has("meta") ? obj.get("meta").getAsInt() : 0;
		int size = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
		ItemStack stack = new ItemStack(item, size, meta);
		if(obj.has("nbt"))
		{
			stack.setTagCompound(ScavengeAPI.INSTANCE.getCompound(obj.get("nbt").getAsString()));
		}
		return stack;
	}
	
	public static ItemStack createCompareStack(JsonObject obj)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(obj.get("name").getAsString()));
		if(item == null)
		{
			return null;
		}
		int meta = obj.has("meta") ? obj.get("meta").getAsInt() : Short.MAX_VALUE;
		int size = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
		return new ItemStack(item, size, meta);
	}
		
	public static BlockEntry createBlockWithMeta(JsonObject obj)
	{
		Block block = Block.getBlockFromName(obj.get("name").getAsString());
		if(block == null)
		{
			return null;
		}
		return new BlockEntry(block, obj.has("meta") ? obj.get("meta").getAsInt() : 0);
	}
	
	public static BlockEntry createCompareBlockEntry(JsonObject obj)
	{
		Block block = Block.getBlockFromName(obj.get("name").getAsString());
		if(block == null)
		{
			return null;
		}
		return new BlockEntry(block, obj.has("meta") ? obj.get("meta").getAsInt() : Short.MAX_VALUE);
	}
	
	public static FluidStack createFluidStack(JsonObject obj)
	{
		return FluidRegistry.getFluidStack(obj.get("name").getAsString(), getOrDefault(obj, "amount", 1000));
	}
	
	public static BlockPos createPosition(JsonObject obj)
	{
		return new BlockPos(getOrDefault(obj, "x", 0), getOrDefault(obj, "y", 0), getOrDefault(obj, "z", 0));
	}
}
