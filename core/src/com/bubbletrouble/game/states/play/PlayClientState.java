package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGame;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.database.tables.Score;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.objects.player.UpdateNameAction;
import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionAction;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.database.ScoreAddDatabaseOperation;
import com.bubbletrouble.game.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.packets.requsets.DisconnectRequest;
import com.bubbletrouble.game.states.connection.ConnectionData;
import com.bubbletrouble.game.states.connection.PreReconnectionState;
import com.bubbletrouble.game.states.interfaces.PacketsSender;
import com.esotericsoftware.kryonet.Client;

import utils.Caster;
import utils.Sleeper;

public class PlayClientState extends PlayState implements PacketsSender
{
	Client client;
	private final ConnectionData data;
	private final Texture background = ShooterGame.assets.get("background.jpg");
	private final Music backgroundMusic = ShooterGame.assets.get("backgroundMusic.mp3");

	public PlayClientState(Client client, ConnectionData data)
	{
		backgroundMusic.play();
		this.client = client;
		this.data = data;
		PlayerProduceInfo info = new PlayerProduceInfo(client.getID());
		info.name = data.getNickname();
		UpdateNameAction action = new UpdateNameAction();
		action.name = data.getNickname();
		sendAction(action, client.getID());
		addObject(info);
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
		pushNewObjects();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(background, 0, 0);
		super.render(batch);
	}

	@Override
	public void update()
	{
		shakeHandle();
		gameObjects.forEach((id, object) -> object.clientUpdate());
		pushNewObjects();
		clearGarbage();
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

	public void addObjects(ProduceInfo[] produceInfos)
	{
		for (ProduceInfo produceInfo : produceInfos)
			addObject(produceInfo);
	}

	public void applyChanges(ActionInfo actionInfo)
	{
		GameObject object = Sleeper.sleepUntillReceiveOtherThanNull(() -> getObject(actionInfo.targetId), 2000);
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
	public GameObject removeObject(GameObject object)
	{
		System.out.println(this.getClass().getSimpleName() + " Removing object id: " + object.getId() + " Type: "
				+ object.getClass().getSimpleName());
		if (object.getId() == client.getID())
		{
			Player userPlayer = Caster.cast(super.removeObject(object), Player.class);
			Score score = new Score(userPlayer.getName(), userPlayer.getPoints());
			client.sendTCP(new ScoreAddDatabaseOperation(score));
			client.sendTCP(new DisconnectRequest());
			ShooterGameClient.states.set(new PreReconnectionState(client, data));
			return userPlayer;
		} else
			return super.removeObject(object);
	}

}
