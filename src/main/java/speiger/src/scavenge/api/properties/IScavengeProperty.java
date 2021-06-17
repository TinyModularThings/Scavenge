package speiger.src.scavenge.api.properties;

import net.minecraft.util.text.ITextComponent;
import speiger.src.scavenge.api.ScavengeRegistry;
import speiger.src.scavenge.api.jei.ComponentBuilder;

public interface IScavengeProperty
{
	public default boolean isIncompatible(IScavengeProperty other) { return ScavengeRegistry.INSTANCE.isIncompatible(getClass(), other.getClass()); }
	public ITextComponent getJEIDescription();
	public boolean shouldShowJEI();
	public void buildJEIComponent(ComponentBuilder builder);
}
