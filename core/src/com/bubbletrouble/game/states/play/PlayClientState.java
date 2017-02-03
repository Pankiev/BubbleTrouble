package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.objects.Obstacle;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.ObstacleAddInfo;
import com.bubbletrouble.game.server.packets.ProduceInfo;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;
import com.bubbletrouble.game.states.play.actions.UpdateAngleAction;
import com.esotericsoftware.kryonet.Client;

public class PlayClientState extends PlayState
{
	Client client;

	public PlayClientState(Client client)
	{
		this.client = client;
		addPlayer(new PlayerAddInfo(client.getID()));
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
		sendAction(action);
	}

	public void sendAction(Action action)
	{
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.targetId = client.getID();
		actionInfo.action = action;
		client.sendTCP(actionInfo);
	}

	public void applyChanges(ActionInfo actionInfo)
	{
		Player player = getPlayer(actionInfo.targetId);
		actionInfo.action.applyChangesToOther(player);
	}

	public void addObstacle(ObstacleAddInfo obstacleAddInfo)
	{
		Obstacle obstacle = new Obstacle();
		obstacle.setPosition(obstacleAddInfo.x, obstacleAddInfo.y);
		gameObjects.add(obstacle);
	}

	public void addObjects(ProduceInfo[] produceInfos)
	{
		for (ProduceInfo produceInfo : produceInfos)
			addObject(produceInfo);
	}
}
