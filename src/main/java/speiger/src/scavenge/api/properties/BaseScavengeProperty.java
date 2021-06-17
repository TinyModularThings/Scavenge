package speiger.src.scavenge.api.properties;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import speiger.src.scavenge.api.IScavengeBuilder;
import speiger.src.scavenge.api.ScavengeRegistry;
import speiger.src.scavenge.api.jei.ComponentBuilder;
import speiger.src.scavenge.api.misc.JsonUtils;
import speiger.src.scavenge.api.value.BooleanValue;
import speiger.src.scavenge.api.value.IValue;
import speiger.src.scavenge.api.value.StringValue;

public class BaseScavengeProperty implements IScavengeProperty
{
	protected boolean custom;
	protected ITextComponent description;
	protected boolean visible;
	
	@Override
	public ITextComponent getJEIDescription()
	{
		return description;
	}
	
	@Override
	public boolean shouldShowJEI()
	{
		return visible;
	}
	
	@Override
	public void buildJEIComponent(ComponentBuilder builder)
	{
		builder.addText(description);
	}
	
	public abstract static class BasePropertyBuilder<T extends BaseScavengeProperty> implements IScavengeBuilder<T>
	{
		public ITextComponent getDefaultMessage(Object...data)
		{
			ResourceLocation id = ScavengeRegistry.INSTANCE.getBuilderId(this);
			return new TranslationTextComponent(id.getNamespace()+"."+id.getPath()+".desc", data);
		}
		
		protected Object[] getTranslationObjects(T src)
		{
			return new Object[0];
		}
		
		protected JsonObject serializeJEI(JsonObject obj, T src)
		{
			if(src.description != null && src.custom)
			{
				obj.add("jei_description", ITextComponent.Serializer.toJsonTree(src.description));
				obj.addProperty("jei_simple", false);
			}
			obj.addProperty("jei_visible", src.visible);
			return obj;
		}
		
		protected T deserializeJEI(JsonObject obj, T src)
		{
			src.visible = JsonUtils.getOrDefault(obj, "jei_visible", true);
			if(obj.has("jei_description"))
			{
				src.description = JsonUtils.getOrDefault(obj, "jei_simple", true) ? new StringTextComponent(obj.get("jei_description").getAsString()) : ITextComponent.Serializer.fromJson(obj.get("jei_description"));
				src.custom = true;
			}
			else src.description = getDefaultMessage(getTranslationObjects(src));
			return src;
		}
		
		protected void serializeJEI(T src, PacketBuffer buffer)
		{
			buffer.writeBoolean(src.visible);
			buffer.writeBoolean(src.custom);
			buffer.writeBoolean(src.description != null);
			if(src.description != null) buffer.writeComponent(src.description);
		}
		
		protected T deserializeJEI(T src, PacketBuffer buffer)
		{
			src.visible = buffer.readBoolean();
			src.custom = buffer.readBoolean();
			if(buffer.readBoolean()) src.description = buffer.readComponent();
			return src;
		}
		
		protected void addJEI(Consumer<IValue> values)
		{
			ITextComponent defaultValue = getDefaultMessage();
			values.accept(new BooleanValue("inverted", false).setOptional(true).setDescription("If the JEI description should be shown"));
			values.accept(new StringValue("jei_description", defaultValue == null ? null : defaultValue.getString()).setOptional(true).setDescription("JEI Description that should be displayed"));
			values.accept(new BooleanValue("jei_simple").setOptional(true).setDescription("If the jei_description should be treated as a Simple String or a full ITextComponent JsonObject"));
		}
	}
}
