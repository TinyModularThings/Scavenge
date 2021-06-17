package speiger.src.scavenge.api.properties;

public abstract class BaseScavengeEffect extends BaseScavengeProperty implements IScavengeEffect
{
	
	public static abstract class BaseEffectBuilder<T extends BaseScavengeEffect> extends BasePropertyBuilder<T>
	{
		@Override
		public OperationType getType()
		{
			return OperationType.EFFECT;
		}
	}
}

