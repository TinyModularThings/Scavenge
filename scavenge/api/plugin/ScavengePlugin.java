package scavenge.api.plugin;

import net.minecraftforge.common.config.Configuration;
import scavenge.api.ScavengeAPI;

/**
 * 
 * @author Speiger
 * 
 * Plugin class where addons are loaded from
 * Just add @ScavengeLoaded and it will be automatically loaded
 */
public interface ScavengePlugin
{
	/**
	 * Function to check if the plugin can be loaded
	 * @param config For an optional config option if wanted
	 * @return true = load, false = not load
	 */
	public boolean shouldLoad(Configuration config);
	
	/**
	 * Loads the plugin on PreInit after everything else is done
	 * @param registry API access makes it faster to see
	 */
	public void loadPlugin(ScavengeAPI registry);
	
	/**
	 * Loads the plugin on PostLoad if that is necessary.
	 * @param registry API access makes it faster to see
	 */
	public void postLoadPlugin(ScavengeAPI registry);
}
