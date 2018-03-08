package scavenge.api.math;

import java.util.List;

import com.google.gson.JsonObject;

import scavenge.api.IScavengeFactory;

public interface IMathRegistry
{
	public IMathOperation getOperation(String id, JsonObject obj);
	
	public IMathModifier getModifier(String id, JsonObject obj);
	
	public void registerOperationFactory(IScavengeFactory<IMathOperation> factory);
	
	public void registerModifierFactory(IScavengeFactory<IMathModifier> factory);
	
	public List<IScavengeFactory<IMathOperation>> getOperations();
	
	public List<IScavengeFactory<IMathModifier>> getModifiers();
}
