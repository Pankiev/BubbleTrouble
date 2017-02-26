package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.Gdx;
import com.bubbletrouble.game.libgdxcommon.input.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.input.KeyHandler;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.player.MoveDownAction;
import com.bubbletrouble.game.objects.player.MoveLeftAction;
import com.bubbletrouble.game.objects.player.MoveRightAction;
import com.bubbletrouble.game.objects.player.MoveUpAction;
import com.bubbletrouble.game.packets.requsets.AddObstacleRequest;
import com.bubbletrouble.game.packets.requsets.DisconnectRequest;
import com.bubbletrouble.game.packets.requsets.ShootRequest;

public class PlayInputHandler extends InputProcessorAdapter
{
	private PlayClientState playState;

	public PlayInputHandler(PlayClientState playState)
	{
		this.playState = playState;
	}

	private long getPlayerId()
	{
		return playState.client.getID();
	}

	public class RightKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveRightAction(), getPlayerId());
		}
	}

	public class LeftKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveLeftAction(), getPlayerId());
		}
	}

	public class UpKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveUpAction(), getPlayerId());
		}
	}

	public class DownKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.sendAction(new MoveDownAction(), getPlayerId());
		}
	}

	public class FKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.send(new DisconnectRequest());
		}
	}

	public class QKeyHandler implements KeyHandler
	{
		public void handle()
		{
			GameObject player = playState.getItsOwnPlayer();
			ShootRequest request = new ShootRequest();
			request.id = player.getId();
			request.mouseX = Gdx.input.getX();
			request.mouseY = -Gdx.input.getY() + Gdx.graphics.getHeight();
			playState.send(request);
		}
	}

	public class NKeyHandler implements KeyHandler
	{
		public void handle()
		{
			playState.send(new AddObstacleRequest());
		}
	}
}
