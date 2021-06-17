package speiger.src.scavenge.api.jei;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;

public interface IInfoComponent
{
	public void render(MatrixStack stack, FontRenderer font, float x, float y);
	public boolean hasSubComponents();
	public List<IInfoComponent> getSubComponents();
}
