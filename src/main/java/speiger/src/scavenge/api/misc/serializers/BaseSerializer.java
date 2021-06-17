package speiger.src.scavenge.api.misc.serializers;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public abstract class BaseSerializer<T> implements JsonDeserializer<T>, JsonSerializer<T>
{
	
}
