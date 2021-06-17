package speiger.src.scavenge.api.storage;

import java.util.function.Consumer;

public interface IInteractable<T>
{
	public void get(Consumer<T> interaction);
	public void remove();
}
