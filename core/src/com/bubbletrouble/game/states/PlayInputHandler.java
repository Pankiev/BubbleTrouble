package com.bubbletrouble.game.states;

import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.KeyHandler;
import com.bubbletrouble.game.libgdxcommon.communication.action.Action;
import com.bubbletrouble.game.objects.actions.PlayerActions;
import com.bubbletrouble.game.server.packets.ActionInfo;
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
			sendAction(PlayerActions.moveRight);
		}
	}

	public class LeftKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(PlayerActions.moveLeft);
		}
	}

	public class UpKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(PlayerActions.moveUp);
		}
	}

	public class DownUpKeyHandler implements KeyHandler
	{
		public void handle()
		{
			sendAction(PlayerActions.moveDown);
		}
	}
}
