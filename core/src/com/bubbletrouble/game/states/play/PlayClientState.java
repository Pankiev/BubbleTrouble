package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Obstacle;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Info;
import com.bubbletrouble.game.server.packets.action.Action;
import com.bubbletrouble.game.server.packets.action.ActionInfo;
import com.bubbletrouble.game.server.packets.action.CollisionAction;
import com.bubbletrouble.game.server.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.server.packets.action.PositionUpdateInfo;
import com.bubbletrouble.game.server.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.server.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.play.actions.UpdateAngleAction;
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
		Obstacle obstacle = new Obstacle(obstacleAddInfo.id);
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

	public void sendInfo(Info info)
	{
		client.sendTCP(info);
	}

	public Player getItsOwnPlayer()
	{
		GameObject object = gameObjects.get((long) client.getID());
		Player player = Caster.castToPlayer(object);
		return player;
	}
}
