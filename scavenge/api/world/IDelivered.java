package scavenge.api.world;

public interface IDelivered<T>
{
	public T get();
	
	public boolean exists();
	
	public void onChanged();
	
	public void remove();
}
