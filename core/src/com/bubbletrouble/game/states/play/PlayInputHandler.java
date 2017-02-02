package com.bubbletrouble.game.states.play;

import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.KeyHandler;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.states.play.actions.MoveDownAction;
import com.bubbletrouble.game.states.play.actions.MoveLeftAction;
import com.bubbletrouble.game.states.play.actions.MoveRightAction;
import com.bubbletrouble.game.states.play.actions.MoveUpAction;
import com.esotericsoftware.kryonet.Client;

public class PlayInputHandler extends InputProcessorAdapter
{
	private PlayClientState playState;

	public PlayInputHandler(PlayClientState playState)
	{
		this.playState = playState;
	}

	private void sendAction(Action action)
	{
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.targetId = client().getID();
		actionInfo.action = action;
		client().sendTCP(actionInfo);
	}

	private Client client()
	{
		return playState.client;
	}

	public class RightKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(new MoveRightAction());
		}
	}

	public class LeftKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(new MoveLeftAction());
		}
	}

	public class UpKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(new MoveUpAction());
		}
	}

	public class DownKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(new MoveDownAction());
		}
	}
}
