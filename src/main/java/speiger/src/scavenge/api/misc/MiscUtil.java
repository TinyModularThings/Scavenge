package speiger.src.scavenge.api.misc;

import java.math.RoundingMode;
import java.util.Optional;

import com.google.common.math.DoubleMath;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class MiscUtil
{
	static final Style DEFAULT_STYLE = Style.EMPTY.withItalic(false).withColor(TextFormatting.AQUA);
	public static int getXP(PlayerEntity player)
	{
		return (int)(getXPForLvl(player.experienceLevel) + (DoubleMath.roundToInt(player.experienceProgress * player.getXpNeededForNextLevel(), RoundingMode.HALF_UP)));
	}
	
	public static int getXPForLvl(int level)
	{
		if(level < 0)
			return Integer.MAX_VALUE;
		if(level <= 15)
			return level * level + 6 * level;
		if(level <= 30)
			return (int)(((level * level) * 2.5D) - (40.5D * level) + 360.0D);
		return (int)(((level * level) * 4.5D) - (162.5D * level) + 2220.0D);
	}
	
	public static int getLvlForXP(int totalXP)
	{
		int result = 0;
		while(getXPForLvl(result) <= totalXP)
		{
			result++;
		}
		return --result;
	}
	
	public static void removeRecursive(CompoundNBT source, CompoundNBT toDelete)
	{
		for(String s : toDelete.getAllKeys())
		{
			INBT nbt = toDelete.get(s);
			if(nbt.getId() == 10 && source.contains(s, 10))
			{
				CompoundNBT subSource = source.getCompound(s);
				removeRecursive(subSource, (CompoundNBT)nbt);
				if(!subSource.isEmpty())
					continue;
			}
			source.remove(s);
		}
	}
	
	public static void addTooltip(ItemStack stack, ITextComponent s)
	{
		CompoundNBT nbt = stack.getOrCreateTagElement("display");
		ListNBT list = nbt.getList("Lore", 8);
		list.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(s.copy().withStyle(DEFAULT_STYLE))));
		nbt.put("Lore", list);
	}
	
	public static int getXZDistance(BlockPos from, BlockPos to)
	{
		long x = from.getX() - to.getX();
		long z = from.getZ() - to.getZ();
		return (int)Math.sqrt(x * x + z * z);
	}
	
	public static BlockState buildProperty(BlockState state, String propertyName, String value)
	{
		Property<?> property = state.getBlock().getStateDefinition().getProperty(propertyName);
		if(property == null) return state;
		Optional<?> requested = property.getValue(value);
		return requested.isPresent() ? setValue(state, property, (Comparable<?>)requested.get()) : state;
	}
	
	public static String getPropertyValue(BlockState state, String propertyName)
	{
		Property<?> property = state.getBlock().getStateDefinition().getProperty(propertyName);
		return property == null ? null : getValue(property, property.value(state).value());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> BlockState setValue(BlockState state, Property<T> property, Comparable<?> value)
	{
		return state.setValue(property, (T)value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> String getValue(Property<T> p_209555_0_, Comparable<?> p_209555_1_)
	{
		return p_209555_0_.getName((T)p_209555_1_);
	}
	
	public static String firstLetterUppercase(String string) {
		if(string == null || string.isEmpty()) return string;
		String first = Character.toString(string.charAt(0));
		return string.replaceFirst(first, first.toUpperCase());
	}
	
	public static String toPascalCase(String input) {
		StringBuilder builder = new StringBuilder();
		for(String s : input.replaceAll("_", " ").split(" ")) {
			builder.append(firstLetterUppercase(s)).append(" ");
		}
		return builder.substring(0, builder.length() - 1);
	}
	
}
