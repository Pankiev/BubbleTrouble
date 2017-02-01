package com.bubbletrouble.game.libgdxcommon;

public class BadTypeException extends GameException
{
	public BadTypeException(String expected)
	{
		super("Bad type, " + expected + " expected");
	}

}
