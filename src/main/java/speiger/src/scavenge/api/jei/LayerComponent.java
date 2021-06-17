package speiger.src.scavenge.api.jei;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LayerComponent implements IInfoComponent
{
	ITextComponent name;
	LayerComponent parent;
	List<IInfoComponent> children = new ObjectArrayList<>();
	
	public LayerComponent(String name)
	{
		this(new TranslationTextComponent(name));
	}
	
	public LayerComponent(ITextComponent name)
	{
		this.name = name;
	}
	
	public LayerComponent addChild(IInfoComponent child)
	{
		children.add(child);
		if(child instanceof LayerComponent) ((LayerComponent)child).parent = this;
		return this;
	}
	
	@Override
	public void render(MatrixStack stack, FontRenderer font, float x, float y)
	{
		font.draw(stack, name.getVisualOrderText(), x, y, 4210752);
	}
	
	public ITextComponent getName()
	{
		return name;
	}
	
	@Override
	public boolean hasSubComponents()
	{
		return children.size() > 0;
	}
	
	@Override
	public List<IInfoComponent> getSubComponents()
	{
		return children;
	}
}
