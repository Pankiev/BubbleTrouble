package com.bubbletrouble.game.states;

import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.KeyHandler;

public class PlayInputHandler extends InputProcessorAdapter
{
	PlayState playState;

	public PlayInputHandler(PlayState state)
	{
		playState = state;
	}

	public class RightKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.player.moveRight();
		}
	}
}
