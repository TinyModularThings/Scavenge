package scavenge.api.loot;

import scavenge.api.IScavengeFactory;

public interface ILootFactory extends IScavengeFactory<ILootProperty>
{
	/**
	 * Function for the documentation if the LootProperty is active/passive
	 * @return if the lootproperty is active or passive
	 */
	public boolean isActiveProperty();
}
