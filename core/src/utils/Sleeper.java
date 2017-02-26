package utils;

import java.util.function.Supplier;

import com.bubbletrouble.game.libgdxcommon.exception.GameException;

public class Sleeper
{
	private final static int WAITING_CHUNK = 10;
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

	public static <T> T sleepUntillReceiveOtherThanNull(Supplier<T> supplier, int timeout)
	{
		int waitingTime = 0;
		T resource = supplier.get();
		while (resource == null)
		{
			sleep(WAITING_CHUNK);
			waitingTime += WAITING_CHUNK;
			if (waitingTime > timeout)
				throw new TimeoutException();
			resource = supplier.get();
		}
		return resource;
	}
	
	public static class TimeoutException extends GameException
	{}
}
