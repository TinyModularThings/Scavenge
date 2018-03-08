package scavenge.api.math.impl;

import scavenge.api.math.IMathModifier;

public abstract class BaseMathModifier implements IMathModifier
{
	String id;
	
	public BaseMathModifier(String id)
	{
		this.id = id;
	}
	
	@Override
	public String getID()
	{
		return id;
	}
	
}
