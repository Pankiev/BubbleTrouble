package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.esotericsoftware.kryonet.Server;

public class PlayServerState extends PlayState
{
	Server server;

	public PlayServerState(Server server)
	{
		this.server = server;
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void render(SpriteBatch batch)
	{
	}

	public void makeAction(ActionInfo actionInfo)
	{
		GameObject player = players.get(actionInfo.targetId);
		Action action = actionInfo.action;
		action.makeAction(player);
	}

}
