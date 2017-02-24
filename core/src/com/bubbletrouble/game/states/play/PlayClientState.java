package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.Obstacle;
import com.bubbletrouble.game.objects.bullet.UpdateAngleAction;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionAction;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.action.PositionUpdateInfo;
import com.bubbletrouble.game.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.packets.requsets.DisconnectRequest;
import com.bubbletrouble.game.states.connection.ConnectionState;
import com.bubbletrouble.game.states.connection.PreReconnectionState;
import com.esotericsoftware.kryonet.Client;

import utils.Caster;

public class PlayClientState extends PlayState
{

	Client client;

	public PlayClientState(Client client)
	{
		this.client = client;
		addObject(new PlayerProduceInfo(client.getID()));
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
		if (!client.isConnected())
		{
			BubbleTroubleGameClient.states.set(new ConnectionState(client));
			return;
		}

		inputHandler.process();
		UpdateAngleAction action = new UpdateAngleAction();
		action.mousePosition = new Vector2(Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight());
		sendAction(action, client.getID());
	}

	public void sendAction(Action action, long id)
	{
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.targetId = id;
		actionInfo.action = action;
		client.sendTCP(actionInfo);
	}

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

	public void update(PositionUpdateInfo produceInfo)
	{
		GameObject toUpdate = getObject(produceInfo.id);
		produceInfo.update(toUpdate);
	}

	public void send(Object info)
	{
		client.sendTCP(info);
	}

	public Player getItsOwnPlayer()
	{
		GameObject object = gameObjects.get((long) client.getID());
		Player player = Caster.cast(object, Player.class);
		return player;
	}

	@Override
	public synchronized void removeObject(GameObject object)
	{
		if (object.getId() == client.getID())
		{
			client.sendTCP(new DisconnectRequest());
			BubbleTroubleGameClient.states.set(new PreReconnectionState(client));
		} else
			super.removeObject(object);
	}

}
