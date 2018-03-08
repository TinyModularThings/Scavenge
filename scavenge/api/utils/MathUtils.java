package scavenge.api.utils;

import java.math.RoundingMode;

import com.google.common.math.DoubleMath;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class MathUtils
{
	public static int getXZDistance(BlockPos pos, BlockPos other)
	{
		long x = pos.getX() - other.getX();
		long z = pos.getZ() - other.getZ();
		return (int)Math.sqrt((x * x) + (z * z));
	}
	
	public static int getXP(EntityPlayer player)
	{
		return (int)(getXPForLvl(player.experienceLevel) + (DoubleMath.roundToInt(player.experience * player.xpBarCap(), RoundingMode.HALF_UP)));
	}
	
	public static int getXPForLvl(int level) 
	{
		if (level < 0) return Integer.MAX_VALUE;
		if (level <= 15)return level * level + 6 * level;
		if (level <= 30)return (int) (((level * level) * 2.5D) - (40.5D * level) + 360.0D);
		return (int) (((level * level) * 4.5D) - (162.5D * level) + 2220.0D);
	}

	public static int getLvlForXP(int totalXP)
	{
		int result = 0;
		while (getXPForLvl(result) <= totalXP) 
		{
			result++;
		}
		return --result;
	}
}
