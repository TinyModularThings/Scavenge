package scavenge.api.block;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author Speiger
 * 
 * Class for Block Effects/Conditions
 * IResourceCondition or / IResourceEffect have to be implemented
 */
public interface IResourceProperty
{
	/**
	 * Function to validate that your property is compatible with the other one.
	 * Its called both sided if one of the two is incompatible with each other then it will not add it to the builder
	 * @param property other property to check if it is compatible to yours
	 * @return true if compatible false if not
	 */
	public boolean canCombine(IResourceProperty property);
	
	/**
	 * ID of the Effect/Condition, has to match the Factory Version
	 * @return the ID for this Effect/Condition
	 */
	public String getID();
	
	/**
	 * Function to add Data into JEI
	 * @param collector the JEICollector
	 */
	public void addJEIData(IJEIBlockHandler collector);
	
	/**
	 * If the data should be shown in JEI or not
	 * @return if the data should be shown in JEI
	 */
	public boolean shouldShowInJEI();
	
	public static interface IJEIBlockHandler
	{
		/**
		 * Info Text thats being added that gets its own line in the
		 * conditions/effects gui.
		 * @param s The text that should be displayed
		 */
		public void addInfo(String s);
		
		/**
		 * Allows to add a Required Item to the JEIGui
		 * @param stack the Stack that should be required
		 */
		public void addRequiredItem(ItemStack stack);
		
		/**
		 * If a block gets transformed into another one
		 * this should show it
		 * @param stack Block & metadata as stack
		 * @param chance how likely it is that it transforms
		 */
		public void addTransformation(ItemStack stack, double chance);
		
		/**
		 * If something can't be explained in 1 line or it requires its own
		 * line then this can make its own tab (recursively).
		 * @param layerName The name of the layer (short explanation)
		 */
		public void addExtraLayer(String layerName);
		
		/**
		 * After a layer was created the layer has to be finished else it will not work properly.
		 * this is the function for it.
		 */
		public void finishLayer();
	}
}
