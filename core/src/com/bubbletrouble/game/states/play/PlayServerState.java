package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.CollisionAction;
import com.bubbletrouble.game.server.packets.CollisionActionInfo;
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
		GameObject object = gameObjects.get(actionInfo.targetId);
		Action action = actionInfo.action;
		action.makeAction(object);
	}

	public void makeAction(CollisionActionInfo actionInfo)
	{
		GameObject object = gameObjects.get(actionInfo.targetId);
		CollisionAction action = actionInfo.action;
		action.makeAction(object, gameObjects.values());
	}

}
