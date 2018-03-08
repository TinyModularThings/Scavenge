package scavenge.api.math.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import scavenge.api.ScavengeAPI;
import scavenge.api.math.IMathModifier;
import scavenge.api.math.IMathOperation;
import scavenge.api.utils.JsonUtil;

public abstract class BaseMathOperation implements IMathOperation
{
	String id;
	List<IMathModifier> math = new ArrayList<IMathModifier>();
	
	public BaseMathOperation(String id)
	{
		this.id = id;
	}
	
	public void addMathModifiers(JsonElement element)
	{
		JsonUtil.convertToObject(element, new Consumer<JsonObject>(){
			@Override
			public void accept(JsonObject t)
			{
				IMathModifier mod = ScavengeAPI.INSTANCE.getMathModifier(t.get("type").getAsString(), t);
				if(mod != null)
				{
					math.add(mod);
				}
			}
		});
	}
	
	public long getValue(long base)
	{
		for(IMathModifier mod : math)
		{
			base = mod.modify(base);
		}
		return base;
	}
	
	@Override
	public String getID()
	{
		return id;
	}
	
}
