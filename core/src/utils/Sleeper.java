package utils;

import com.bubbletrouble.game.libgdxcommon.GameException;

public class Sleeper
{
	public static void sleep(int miliseconds)
	{
		try
		{
			Thread.sleep(miliseconds);
		} catch (InterruptedException e)
		{
			throw new GameException("Couldn't freeze thread");
		}
	}
}
