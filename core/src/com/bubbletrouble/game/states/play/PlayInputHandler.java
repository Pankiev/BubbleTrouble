package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.Gdx;
import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.KeyHandler;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.requsets.AddObstacleRequest;
import com.bubbletrouble.game.server.packets.requsets.ShootRequest;
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

	public class QKeyHandler implements KeyHandler
	{
		public void handle()
		{
			Player player = playState.getItsOwnPlayer();
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
