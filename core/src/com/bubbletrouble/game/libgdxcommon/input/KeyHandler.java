package com.bubbletrouble.game.libgdxcommon.input;

public interface KeyHandler
{
	public static final KeyHandler EMPTY = new KeyHandler()
	{
		@Override
		public void handle()
		{
		}
	};

	void handle();
}
