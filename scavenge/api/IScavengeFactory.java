package scavenge.api;

import java.util.function.BiConsumer;

import com.google.gson.JsonObject;

import scavenge.api.autodoc.MapElement;
import scavenge.api.utils.CompatState;

/**
 * 
 * @author Speiger
 *
 * Basic Factory class for Scavenge registries. These contain most of the functions required
 */
public interface IScavengeFactory<T>
{
	/**
	 * The ID of the Entry that it will create.
	 * This is being used when a entry is being searched for in the scripts
	 * @return the Id of the entry this factory creates
	 */
	public String getID();
	
	/**
	 * The Create Function that creates the Object from JsonData into actual code.
	 * When created by this the instance has to be unique
	 * @param obj The Data Injected
	 * @return the Code version of that Entry that it produces
	 */
	public T createObject(JsonObject obj);
	
	/**
	 * Mainly for auto documentation.
	 * This function asks the Factory to provide all Incompats or partly Incompatible Other factories
	 * @param states Where you add the incompats to. Add Only so it can't be modified
	 */
	public void addIncompats(BiConsumer<String, CompatState> states);
	
	/**
	 * Completely for auto-documentation.
	 * This allows to give a description and add parameters (with layers) to print automatically.
	 * how to use it is very simple to use.
	 * setDescription() sets the description of the Object that its creating
	 * while addElement() adds parameters that are required or optional
	 * These parameters can also have descriptions to explain what they are and layers are also possible
	 * A basic example can be found in {@scavenge.api.block.impl.BaseResourceFactory}
	 * @return the Auto Documentation object
	 */
	public MapElement getDocumentation();
	
	/**
	 * Function to print out an example of the object that would be created
	 * A backwards way to create the json data from code
	 * the ID doesn't need to be injected that happens automatically after this function call
	 * @param obj Data to inject
	 */
	public void addExample(JsonObject obj);
}
