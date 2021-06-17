package speiger.src.scavenge.api.properties;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public interface IScavengeCondition extends IScavengeProperty
{
	public boolean test(BlockState state, World world, BlockPos pos, Direction side, PlayerEntity player, Hand hand, boolean client);
	
	public ITextComponent getFailMessage();
}