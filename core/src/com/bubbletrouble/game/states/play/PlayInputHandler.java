package com.bubbletrouble.game.states.play;

import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.KeyHandler;
import com.bubbletrouble.game.states.play.actions.MoveDownAction;
import com.bubbletrouble.game.states.play.actions.MoveLeftAction;
import com.bubbletrouble.game.states.play.actions.MoveRightAction;
import com.bubbletrouble.game.states.play.actions.MoveUpAction;

public class PlayInputHandler extends InputProcessorAdapter
{
	private PlayClientState playState;

	public PlayInputHandler(PlayClientState playState)
	{
		this.playState = playState;
	}

	public class RightKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveRightAction());
		}
	}

	public class LeftKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveLeftAction());
		}
	}

	public class UpKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveUpAction());
		}
	}

	public class DownKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveDownAction());
		}
	}
}