package scavenge.api.plugin;

public @interface ScavengeLoaded
{
	public String name();
	
	public String version() default "1.0";
}
