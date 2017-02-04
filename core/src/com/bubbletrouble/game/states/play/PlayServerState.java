package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.CollisionAction;
import com.bubbletrouble.game.server.packets.CollisionActionInfo;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.List;

public class PlayServerState extends PlayState
{
	Server server;
	BitmapFont font = new BitmapFont();
	List<String> messages = new ArrayList<String>();
	private float y = 20;

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
		y = 20;
		for (String message : messages) {
			font.draw(batch, message, 20, y);
			y+= 20;
		}

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


	public void addMessage(String message)
    {
		messages.add(message);
	}
}
