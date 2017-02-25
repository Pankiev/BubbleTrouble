package com.bubbletrouble.game.states.play;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.kryonetcommon.IdProvider;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionAction;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.packets.remove.ObjectRemoveInfo;
import com.bubbletrouble.game.states.interfaces.PacketsSender;
import com.esotericsoftware.kryonet.Server;

public class PlayServerState extends PlayState implements PacketsSender
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
	public void update()
	{
		gameObjects.forEach((id, object) -> object.serverUpdate());
		clearGarbage();
	}

	@Override
	public void removeObject(GameObject object)
	{
		super.removeObject(object);
		long id = object.getId();
		server.sendToAllTCP(new ObjectRemoveInfo(id));
	}

	@Override
	public void render(SpriteBatch batch)
	{
		y = 20 * messages.size();
		for (String message : messages)
		{
			font.draw(batch, message, 20, y);
			y -= 20;
		}
		super.render(batch);
	}

	public void makeAction(ActionInfo actionInfo)
	{
		GameObject object = gameObjects.get(actionInfo.targetId);
		if (object != null)
		{
			Action action = actionInfo.action;
			action.makeAction(object);
		}
	}

	public void makeAction(CollisionActionInfo actionInfo)
	{
		GameObject object = gameObjects.get(actionInfo.targetId);
		if (object != null)
		{
			CollisionAction action = actionInfo.action;
			action.makeAction(object, gameObjects.values());
		}
	}

	public void addMessage(String message)
	{
		messages.add(message);
	}

	@Override
	public void sendAction(Action action, long id)
	{
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.action = action;
		actionInfo.targetId = id;
		server.sendToAllTCP(actionInfo);
	}

	@Override
	public void sendAction(CollisionAction action, long id)
	{
		CollisionActionInfo actionInfo = new CollisionActionInfo();
		actionInfo.action = action;
		actionInfo.targetId = id;
		server.sendToAllTCP(actionInfo);
	}

	@Override
	public void send(Object packet)
	{
		server.sendToAllTCP(packet);
	}

	public void addRandomObstacle()
	{
		ObstacleProduceInfo addObstacle = new ObstacleProduceInfo();
		addObstacle.x = randomPosition();
		addObstacle.y = randomPosition();
		addObstacle.id = IdProvider.getNextId();
		server.sendToAllTCP(addObstacle);
		addObject(addObstacle);
	}

	private int randomPosition()
	{
		return new Random().nextInt(500);
	}


}
