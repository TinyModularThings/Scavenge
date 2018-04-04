package scavenge.api.block.impl;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.google.gson.JsonObject;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scavenge.api.ScavengeAPI;
import scavenge.api.block.IResourceCondition;
import scavenge.api.block.IResourceEffect;
import scavenge.api.block.IResourceProperty;
import scavenge.api.utils.JsonUtil;
import scavenge.api.world.IWorldHandler;

public abstract class BaseResourceProperty implements IResourceProperty
{
	String jeiInfo;
	boolean jei;
	String id;
	Set<String> incompats = new HashSet<String>();
	
	public BaseResourceProperty(JsonObject obj, String id)
	{
		this(!JsonUtil.getOrDefault(obj, "disableJEI", false), id);
	}
	
	public BaseResourceProperty(boolean jei, String id)
	{
		this.jei = jei;
		this.id = id;
	}
	
	public void addSelfIncompat()
	{
		incompats.add(id);
	}
	
	public void setJEIInfo(String s)
	{
		jeiInfo = s;
	}
	
	public void addIncompat(String id)
	{
		incompats.add(id);
	}
	
	public void addIncompats(String... ids)
	{
		for(String id : ids)
		{
			incompats.add(id.toLowerCase(Locale.ENGLISH));
		}
	}
	
	@Override
	public void addJEIData(IJEIBlockHandler collector)
	{
		if(jei)
		{
			collector.addInfo(jeiInfo);
		}
	}
	
	@Override
	public boolean canCombine(IResourceProperty property)
	{
		return incompats.isEmpty() || !incompats.contains(property.getID().toLowerCase(Locale.ENGLISH));
	}
	
	@Override
	public String getID()
	{
		return id;
	}
	
	@Override
	public boolean shouldShowInJEI()
	{
		return jei;
	}
	
	public void sendPacketToPlayer(EntityPlayer player, Packet packet)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP)player;
			if(mp.connection == null)
			{
				return;
			}
			mp.connection.sendPacket(packet);
		}
	}
	
	public IWorldHandler getHandler(World world)
	{
		return ScavengeAPI.INSTANCE.getWorldRegistry().getStorageForWorld(world);
	}
	
	protected IResourceCondition createCondition(JsonObject obj)
	{
		String id = obj.get("type").getAsString();
		IResourceProperty prop = ScavengeAPI.INSTANCE.createResourceProperty(id, obj);
		if(prop instanceof IResourceCondition)
		{
			return (IResourceCondition)prop;
		}
		throw new RuntimeException("Property ["+id+"] is not a condition!");
	}
	
	protected IResourceEffect createEffect(JsonObject obj)
	{
		String id = obj.get("type").getAsString();
		IResourceProperty prop = ScavengeAPI.INSTANCE.createResourceProperty(id, obj);
		if(prop instanceof IResourceEffect)
		{
			return (IResourceEffect)prop;
		}
		throw new RuntimeException("Property ["+id+"] is not a Effect!");
	}
	
	protected StatisticsManager getStats(EntityPlayer player)
	{
		if(player instanceof EntityPlayerMP)
		{
			return ((EntityPlayerMP)player).getStatFile();
		}
		return getClientStats(player);
	}
	
	@SideOnly(Side.CLIENT)
	protected StatisticsManager getClientStats(EntityPlayer player)
	{
		if(player instanceof EntityPlayerSP)
		{
			return ((EntityPlayerSP)player).getStatFileWriter();
		}
		return null;
	}
}
