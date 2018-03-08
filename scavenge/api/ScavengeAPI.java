package scavenge.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import net.minecraft.nbt.NBTTagCompound;
import scavenge.api.block.IResourceFactory;
import scavenge.api.block.IResourceProperty;
import scavenge.api.loot.ILootFactory;
import scavenge.api.loot.ILootProperty;
import scavenge.api.math.IMathModifier;
import scavenge.api.math.IMathOperation;
import scavenge.api.math.IMathRegistry;
import scavenge.api.math.impl.ArrayMathOperation.ArrayFactory;
import scavenge.api.math.impl.EmptyMathOperation.EmptyOperationFactory;
import scavenge.api.world.IWorldRegistry;

public final class ScavengeAPI
{
	public static boolean printDocumentation = false;
	public static boolean printInCurseFormat = false;
	public static final Logger APILogger = LogManager.getLogger("ScavengeAPI");
	public static final ScavengeAPI INSTANCE = new ScavengeAPI();
	Map<String, IResourceFactory> blockProperties = new LinkedHashMap<String, IResourceFactory>();
	Map<String, ILootFactory> lootProperties = new LinkedHashMap<String, ILootFactory>();
	Map<String, NBTTagCompound> nbtStorage = new LinkedHashMap<String, NBTTagCompound>();
	IClientRegistry clientRegistry;
	IMathRegistry mathRegistry;
	IWorldRegistry worldRegistry;
	
	public void clearTemporaryStorage()
	{
		nbtStorage.clear();
	}
	
	public void setMathRegistry(IMathRegistry registry)
	{
		if(mathRegistry != null || registry == null)
		{
			return;
		}
		mathRegistry = registry;
		mathRegistry.registerOperationFactory(new EmptyOperationFactory());
		mathRegistry.registerOperationFactory(new ArrayFactory());
	}
	
	public void setWorldRegistry(IWorldRegistry registry)
	{
		if(worldRegistry != null || registry == null)
		{
			return;
		}
		worldRegistry = registry;
	}
	
	public void setClientRegistry(IClientRegistry registry)
	{
		if(clientRegistry != null || registry == null)
		{
			return;
		}
		clientRegistry = registry;
	}
	
	public void registerResourceProperty(IResourceFactory factory)
	{
		String id = factory.getID();
		if(id == null || id.isEmpty())
		{
			APILogger.error("ID for factory ["+factory.toString()+"] is null or Empty. Its required to have a ID!");
			return;
		}
		if(blockProperties.containsKey(id))
		{
			APILogger.error("ID ["+id+"] is already being used!");
			return;
		}
		blockProperties.put(id, factory);
	}
	
	public void registerLootProperty(ILootFactory factory)
	{
		String id = factory.getID();
		if(id == null || id.isEmpty())
		{
			APILogger.error("ID for factory ["+factory.toString()+"] is null or Empty. Its required to have a ID!");
			return;
		}
		if(lootProperties.containsKey(id))
		{
			APILogger.error("ID ["+id+"] is already being used!");
			return;
		}
		lootProperties.put(id, factory);
	}
	
	public void storeNBT(String id, NBTTagCompound nbt)
	{
		if(id == null || id.isEmpty())
		{
			APILogger.error("Compund ["+nbt.toString()+"] is registered with a Empty ID");
			return;
		}
		if(nbt == null)
		{
			APILogger.error("Compound with the ID ["+id+"] is null. That is not allowed");
			return;
		}
		nbtStorage.put(id, nbt.copy());
	}
	
	public IResourceProperty createResourceProperty(String id, JsonObject obj)
	{
		IScavengeFactory<IResourceProperty> factory = blockProperties.get(id);
		if(factory == null)
		{
			throw new RuntimeException("ID ["+id+"] has no factory!");
		}
		return factory.createObject(obj);
	}
	
	public ILootProperty createLootProperty(String id, JsonObject obj)
	{
		IScavengeFactory<ILootProperty> factory = lootProperties.get(id);
		if(factory == null)
		{
			throw new RuntimeException("ID ["+id+"] has no factory!");
		}
		return factory.createObject(obj);
	}
	
	public IMathOperation getMathOperation(String id, JsonObject obj)
	{
		return mathRegistry.getOperation(id, obj);
	}
	
	public IMathModifier getMathModifier(String id, JsonObject obj)
	{
		return mathRegistry.getModifier(id, obj);
	}
	
	public IMathRegistry getMathRegistry()
	{
		return mathRegistry;
	}
	
	public IWorldRegistry getWorldRegistry()
	{
		return worldRegistry;
	}
	
	public IClientRegistry getClientRegistry()
	{
		return clientRegistry;
	}
	
	public boolean hasCompound(String id)
	{
		return nbtStorage.containsKey(id);
	}
	
	public NBTTagCompound getCompound(String id)
	{
		NBTTagCompound nbt = nbtStorage.get(id);
		if(nbt == null)
		{
			return null;
		}
		return nbt.copy();
	}
	
	public List<ILootFactory> getLootFactories()
	{
		return new ArrayList<ILootFactory>(lootProperties.values());
	}
	
	public List<IResourceFactory> getResourceFactory()
	{
		return new ArrayList<IResourceFactory>(blockProperties.values());
	}
}
