package speiger.src.scavenge.api.properties;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import speiger.src.scavenge.api.ScavengeRegistry;
import speiger.src.scavenge.api.jei.ComponentBuilder;
import speiger.src.scavenge.api.math.IMathCondition;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.BooleanValue;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.StringValue;

public abstract class BaseScavengeCondition extends BaseScavengeProperty implements IScavengeCondition
{
	protected ITextComponent errorMesssage;
	protected boolean result;
	
	protected boolean result(boolean value)
	{
		return result == value;
	}
	
	@Override
	public ITextComponent getFailMessage()
	{
		return errorMesssage;
	}
	
	@Override
	public void buildJEIComponent(ComponentBuilder builder)
	{
		if(result || !custom) builder.addText(description);
		else builder.addText(description.copy().append(new TranslationTextComponent("scavenge.inverted")));
	}
	
	public abstract static class BaseConditionBuilder<T extends BaseScavengeCondition> extends BasePropertyBuilder<T>
	{
		protected JsonObject serializeError(JsonObject obj, T src)
		{
			if(!src.result) obj.addProperty("inverted", true);
			if(src.errorMesssage != null)
			{
				obj.add("error_message", ITextComponent.Serializer.toJsonTree(src.errorMesssage));
				obj.addProperty("error_simple", false);
			}
			serializeJEI(obj, src);
			return obj;
		}
		
		protected T deserializeError(JsonObject obj, T src)
		{
			src.result = !JsonUtils.getOrDefault(obj, "inverted", false);
			if(obj.has("error_message")) src.errorMesssage = JsonUtils.getOrDefault(obj, "error_simple", true) ? new StringTextComponent(obj.get("error_message").getAsString()) : ITextComponent.Serializer.fromJson(obj.get("error_message"));
			deserializeJEI(obj, src);
			return src;
		}
		
		protected void serializeError(T src, PacketBuffer buffer)
		{
			buffer.writeBoolean(src.result);
			buffer.writeBoolean(src.errorMesssage != null);
			if(src.errorMesssage != null) buffer.writeComponent(src.errorMesssage);
			serializeJEI(src, buffer);
		}
		
		protected T deserializeError(T src, PacketBuffer buffer)
		{
			src.result = buffer.readBoolean();
			if(buffer.readBoolean()) src.errorMesssage = buffer.readComponent();
			return deserializeJEI(src, buffer);
		}
		
		@Override
		public OperationType getType()
		{
			return OperationType.CONDITION;
		}
		
		protected void addError(Consumer<IValue> values)
		{
			values.accept(new BooleanValue("inverted", false).setOptional(true).setDescription("If the result should be Inverted"));
			values.accept(new StringValue("error_message", null).setOptional(true).setDescription("Error Message that should be displayed"));
			values.accept(new BooleanValue("error_simple").setOptional(true).setDescription("If the error_message should be treated as a Simple String or a full ITextComponent JsonObject"));
			addJEI(values);
		}
		
		protected IMathCondition deserializeCondition(JsonObject obj)
		{
			ScavengeRegistry registry = ScavengeRegistry.INSTANCE;
			return registry.deserializeMathConditions(registry.getConditionObject(obj));
		}
	}
}