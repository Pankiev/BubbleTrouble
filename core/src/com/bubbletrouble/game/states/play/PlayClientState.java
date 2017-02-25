package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.obstacle.Obstacle;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionAction;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.packets.requsets.DisconnectRequest;
import com.bubbletrouble.game.states.connection.ConnectionData;
import com.bubbletrouble.game.states.connection.PreReconnectionState;
import com.bubbletrouble.game.states.interfaces.PacketsSender;
import com.esotericsoftware.kryonet.Client;

import utils.Caster;

public class PlayClientState extends PlayState implements PacketsSender
{
	Client client;
	private ConnectionData data;

	public PlayClientState(Client client, ConnectionData data)
	{
		this.client = client;
		this.data = data;
		PlayerProduceInfo info = new PlayerProduceInfo(client.getID());
		info.name = data.getNickname();
		addObject(info);
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
	}

	@Override
	public void update()
	{
		gameObjects.forEach((id, object) -> object.clientUpdate());
		if (!client.isConnected())
		{
			ShooterGameClient.states.set(new PreReconnectionState(client, data));
			return;
		}

		inputHandler.process();
	}


	@Override
	public void sendAction(Action action, long id)
	{
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.targetId = id;
		actionInfo.action = action;
		client.sendTCP(actionInfo);
	}

	@Override
	public void sendAction(CollisionAction action, long id)
	{
		CollisionActionInfo actionInfo = new CollisionActionInfo();
		actionInfo.targetId = id;
		actionInfo.action = action;
		client.sendTCP(actionInfo);
	}

	public void addObstacle(ObstacleProduceInfo obstacleAddInfo)
	{
		Obstacle obstacle = new Obstacle(this, obstacleAddInfo.id);
		obstacle.setPosition(obstacleAddInfo.x, obstacleAddInfo.y);
		gameObjects.put(obstacleAddInfo.id, obstacle);
	}

	public void addObjects(ProduceInfo[] produceInfos)
	{
		for (ProduceInfo produceInfo : produceInfos)
			addObject(produceInfo);
	}

	public void applyChanges(ActionInfo actionInfo)
	{
		GameObject object = getObject(actionInfo.targetId);
		actionInfo.action.applyChangesToOther(object);
	}

	public void applyChanges(CollisionActionInfo actionInfo)
	{
		GameObject object = getObject(actionInfo.targetId);
		actionInfo.action.applyChangesToOther(object);
	}

	@Override
	public void send(Object info)
	{
		client.sendTCP(info);
	}

	public GameObject getItsOwnPlayer()
	{
		GameObject object = gameObjects.get((long) client.getID());
		GameObject player = Caster.cast(object, Player.class);
		return player;
	}

	@Override
	public synchronized void removeObject(GameObject object)
	{
		if (object.getId() == client.getID())
		{
			client.sendTCP(new DisconnectRequest());
			ShooterGameClient.states.set(new PreReconnectionState(client, data));
		} else
			super.removeObject(object);
	}

}
