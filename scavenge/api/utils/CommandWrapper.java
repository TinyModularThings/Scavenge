package scavenge.api.utils;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class CommandWrapper implements ICommandSender
{
	EntityPlayer player;
	BlockPos pos;
	
	public CommandWrapper(EntityPlayer thePlayer)
	{
		this(thePlayer, thePlayer.getPosition());
	}
	
	public CommandWrapper(EntityPlayer player, BlockPos pos)
	{
		this.player = player;
		this.pos = pos;
	}
	
	@Override
	public String getName()
	{
		return player.getName();
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return player.getDisplayName();
	}
	
	@Override
	public void sendMessage(ITextComponent component)
	{
	}

	@Override
	public boolean canUseCommand(int permLevel, String commandName)
	{
		return true;
	}
	
	@Override
	public BlockPos getPosition()
	{
		return pos;
	}

	@Override
	public Vec3d getPositionVector()
	{
		return new Vec3d(pos).addVector(0.5D, 0.5D, 0.5D);
	}

	@Override
	public World getEntityWorld()
	{
		return player.getEntityWorld();
	}

	@Override
	public Entity getCommandSenderEntity()
	{
		return player;
	}

	@Override
	public boolean sendCommandFeedback()
	{
		return false;
	}

	@Override
	public void setCommandStat(Type type, int amount)
	{
		player.setCommandStat(type, amount);
	}

	@Override
	public MinecraftServer getServer()
	{
		return player.getServer();
	}
}
