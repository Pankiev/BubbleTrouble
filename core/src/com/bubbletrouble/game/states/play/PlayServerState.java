package com.bubbletrouble.game.states.play;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.action.Action;
import com.bubbletrouble.game.server.packets.action.ActionInfo;
import com.bubbletrouble.game.server.packets.action.CollisionAction;
import com.bubbletrouble.game.server.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.server.packets.action.PositionUpdateInfo;
import com.bubbletrouble.game.server.packets.remove.ObjectRemoveInfo;
import com.esotericsoftware.kryonet.Server;

public class PlayServerState extends PlayState
{
	private Server server;
	private BitmapFont font = new BitmapFont();
	private List<String> messages = new ArrayList<String>();
	private float y = 20;

	public PlayServerState(Server server)
	{
		this.server = server;
		font.setColor(0, 0, 0, 0.8f);
	}

	@Override
	public synchronized void update()
	{
		List<GameObject> toDelete = new ArrayList<GameObject>();
		for (GameObject object : gameObjects.values())
		{
			updateObject(object);
			if (object.shouldBeDeleted())
				toDelete.add(object);
		}

		for (GameObject trash : toDelete)
			remove(trash);
	}

	private void updateObject(GameObject object)
	{
		object.update();
		if (object.needsPositionUpdate())
		{
			PositionUpdateInfo info = producePositionUpdateInfo(object);
			server.sendToAllTCP(info);
			object.informAboutPositionUpdate();
		}
	}

	private void remove(GameObject object)
	{
		gameObjects.remove(object.getId());
		server.sendToAllTCP(new ObjectRemoveInfo(object.getId()));
	}

	private PositionUpdateInfo producePositionUpdateInfo(GameObject object)
	{
		PositionUpdateInfo info = new PositionUpdateInfo();
		info.id = object.getId();
		info.x = object.getX();
		info.y = object.getY();
		return info;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		y = 20 * messages.size();
		for (String message : messages) {
			font.draw(batch, message, 20, y);
			y -= 20;
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
