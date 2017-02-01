package com.bubbletrouble.game.libgdxcommon;

public class BadTypeException extends GameException
{
	public BadTypeException(String className)
	{
		super("Bad type, " + className + " expected");
	}

}
