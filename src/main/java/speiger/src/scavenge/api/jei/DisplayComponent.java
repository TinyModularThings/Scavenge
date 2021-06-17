package speiger.src.scavenge.api.jei;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DisplayComponent implements IInfoComponent
{
	ITextComponent text;
	
	public DisplayComponent(String text)
	{
		this(new TranslationTextComponent(text));
	}
	
	public DisplayComponent(ITextComponent text)
	{
		this.text = text;
	}

	@Override
	public void render(MatrixStack stack, FontRenderer font, float x, float y)
	{
		font.draw(stack, text.getVisualOrderText(), x, y, 4210752);
	}

	@Override
	public boolean hasSubComponents()
	{
		return false;
	}

	@Override
	public List<IInfoComponent> getSubComponents()
	{
		return Collections.emptyList();
	}
}
