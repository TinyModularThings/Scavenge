package scavenge.api.utils;

public enum CompatState
{
	INCOMPATIBLE,
	PARTCOMPATIBLE;
	
	public String getDisplayName()
	{
		if(this == CompatState.INCOMPATIBLE)
		{
			return "Completely";
		}
		return "Partly";
	}
}
