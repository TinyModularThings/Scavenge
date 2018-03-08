package scavenge.api.block;

import scavenge.api.IScavengeFactory;

public interface IResourceFactory extends IScavengeFactory<IResourceProperty>
{
	/**
	 * What type it is a condition or a effect or both
	 * @return what type it is
	 */
	public PropertyType getType();
	
	
	public static enum PropertyType
	{
		Effect,
		Condition,
		ConditionEffect;
		
		public String getDisplayName()
		{
			if(this == Effect) return "Effect";
			else if(this == Condition) return "Condition";
			return "Condition, Effect";
		}
	}
}
