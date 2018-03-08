package scavenge.api.loot;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ILootProperty
{
	public static final Random rand = new Random();
	
	/**
	 * @return the ID of the LootProperty thats being used in the JsonScript
	 */
	public String getID();
	
	/**
	 * Function that can compare if the the loot property can be combined with other loot properties
	 * called on both sides
	 * @param loot the other lootPrperty
	 * @return if its compatible or not
	 */
	public boolean canCombine(ILootProperty loot);
	
	/**
	 * @return if the Effect is applied on every single loot call or just when the loot is created (game load)
	 */
	public boolean isActiveProperty();
	
	/**
	 * Function for the Passive Effect on the Itemstack
	 * this is being loaded when the scripts are being build
	 * @param stack that should be modified.
	 * @return the inputstack
	 */
	public ItemStack applyPassiveEffect(ItemStack stack);
	
	/**
	 * Active Loot building call this is called every time the loot is generated.
	 * The CustomData is data thats being send from the BlockProperties (Effect container)
	 * @param stack inputstack
	 * @param customData data from the BlockProperties
	 * @return the inputstack
	 */
	public ItemStack applyActiveEffect(ItemStack stack, NBTTagCompound customData);
	
	//JEIFunctions
	/**
	 * 
	 * @return if the property causes multiple causes of the stack
	 */
	public boolean hasMultiResults();
	
	/**
	 * If the Effect has only 1 effect apply it to this stack and return it
	 * @param stack input stack
	 * @return the inputstack modified
	 */
	public ItemStack applyInfoEffect(ItemStack stack);
	
	/**
	 * If the Property has multiple output possibilities return all of them here.
	 * @param stack input stack
	 * @return all of the possible results
	 */
	public List<ItemStack> applyMultiInfoEffect(ItemStack stack);
	
}
