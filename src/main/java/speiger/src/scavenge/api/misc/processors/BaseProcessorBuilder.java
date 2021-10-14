package speiger.src.scavenge.api.misc.processors;

import net.minecraft.world.gen.feature.template.StructureProcessor;
import speiger.src.scavenge.api.IScavengeBuilder;

public abstract class BaseProcessorBuilder<T extends StructureProcessor> implements IScavengeBuilder<T>
{
	@Override
	public OperationType getType()
	{
		return OperationType.STRUCTURE_PROCESSOR;
	}
}