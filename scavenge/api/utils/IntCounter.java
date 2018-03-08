package scavenge.api.utils;

public class IntCounter
{
	int counter;
	
	public IntCounter()
	{
		this(0);
	}
	
	public IntCounter(int startValue)
	{
		counter = startValue;
	}
	
	public void increase()
	{
		increase(1);
	}
	
	public void increase(int amount)
	{
		counter+=amount;
	}
	
	public void change(int amount, int min, int max)
	{
		counter+=amount;
		if(counter < min) counter = min;
		else if(counter > max) counter = max;
	}
	
	public void decrease()
	{
		decrease(1);
	}
	
	public void decrease(int amount)
	{
		counter-=amount;
	}
	
	public void decrease(int amount, int minimum)
	{
		decrease(amount);
		if(counter < minimum)
		{
			counter = minimum;
		}
	}
	
	public int getCurrentValue()
	{
		return counter;
	}
	
	public void setValue(int value)
	{
		counter = value;
	}
}
