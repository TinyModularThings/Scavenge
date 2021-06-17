package speiger.src.scavenge.api.plugins;

import net.minecraftforge.common.ForgeConfigSpec;
import speiger.src.scavenge.api.ScavengeRegistry;

public interface IScavengePlugin
{
	public void initConfig(ForgeConfigSpec.Builder builder);
	public boolean canLoad();
	public void load(ScavengeRegistry registry);
}
