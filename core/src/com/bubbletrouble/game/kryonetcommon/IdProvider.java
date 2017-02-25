package com.bubbletrouble.game.kryonetcommon;

public class IdProvider
{
	private static long nextObjectId;

	static
	{
		nextObjectId = (long) Integer.MAX_VALUE + 1;
	}

	public static long getNextId()
	{
		if (nextObjectId < 0)
			nextObjectId = Integer.MAX_VALUE + 1;
		nextObjectId++;
		return nextObjectId;
	}
}