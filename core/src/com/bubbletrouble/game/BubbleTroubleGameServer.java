package com.bubbletrouble.game;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.server.packets.PacketsRegisterer;
import com.bubbletrouble.game.server.packets.action.ActionInfo;
import com.bubbletrouble.game.server.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.server.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.server.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;
import com.bubbletrouble.game.server.packets.remove.ObjectRemoveInfo;
import com.bubbletrouble.game.states.play.PlayServerState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class BubbleTroubleGameServer extends ApplicationAdapter
{
	public static final int tcpPort = 8000;
	public static final int udpPort = 8001;

	private Server server;
	private PlayServerState playState;
	private long nextObjectId = (long) Integer.MAX_VALUE + 1;

	SpriteBatch batch;
	@Override
	public void create()
	{
		batch = new SpriteBatch();
		BubbleTroubleGameClient.assets = new Assets();
		BubbleTroubleGameClient.assets.loadAll();
		server = new Server();
		registerPackets();
		server.start();
		tryBindingServer();
		addListeners();
		playState = new PlayServerState(server);
	}

	private void registerPackets()
	{
		Kryo kryo = server.getKryo();
		PacketsRegisterer.registerAllAnnotated(kryo);
		PacketsRegisterer.registerDefaults(kryo);
	}

	private void tryBindingServer()
	{
		try
		{
			server.bind(tcpPort, udpPort);
		} catch (IOException e)
		{
			throw new ServerBindingExcepiton(e.getMessage());
		}
	}


	private void addListeners()
	{
		server.addListener(new ServerListener());
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		playState.render(batch);
		batch.end();
		update();
	}

	private void update()
	{
		playState.update();
	}

	private void userConnected(Connection connection)
	{
		Integer id = connection.getID();
		server.sendToTCP(id, playState.getGameObjects());
		server.sendToAllExceptTCP(id, new PlayerProduceInfo(id));
		playState.addObject(new PlayerProduceInfo(id));
		Log.info(">> Player added " + connection.getID());
		playState.addMessage(">> Player added " + connection.getID());

		addRandomObstacle();
	}

	private void addRandomObstacle()
	{
		ObstacleProduceInfo addObstacle = new ObstacleProduceInfo();
		addObstacle.x = new Random().nextInt(500);
		addObstacle.y = new Random().nextInt(500);
		addObstacle.id = getNextId();
		server.sendToAllTCP(addObstacle);
		playState.addObject(addObstacle);
	}

	private long getNextId()
	{
		if (nextObjectId < 0)
			nextObjectId = Integer.MAX_VALUE + 1;
		nextObjectId++;
		return nextObjectId;
	}

	private void userDisconnected(Connection connection)
	{
		ObjectRemoveInfo removePlayer = new ObjectRemoveInfo();
		removePlayer.id = connection.getID();
		server.sendToAllExceptTCP(connection.getID(), removePlayer);
		playState.removeObject(removePlayer.id);
		Log.info(">> Player removed " + connection.getID());
		playState.addMessage(">> Player removed " + connection.getID());
	}

	private void actionRecieved(ActionInfo actionInfo, Connection source)
	{
		playState.makeAction(actionInfo);
		server.sendToAllTCP(actionInfo);
	}

	private void actionRecieved(CollisionActionInfo actionInfo, Connection source)
	{
		playState.makeAction(actionInfo);
		server.sendToAllTCP(actionInfo);
	}

	private void produceInfoReceived(ProduceInfo produceInfo)
	{
		produceInfo.id = getNextId();
		playState.addObject(produceInfo);
		server.sendToAllTCP(produceInfo);
	}

	private class ServerListener extends Listener
	{
		@Override
		public void connected(Connection connection)
		{
			userConnected(connection);
		}

		@Override
		public void disconnected(Connection connection)
		{
			userDisconnected(connection);
		}

		@Override
		public void received(Connection connection, Object object)
		{
			if (object instanceof ActionInfo)
				actionRecieved((ActionInfo) object, connection);
			else if (object instanceof CollisionActionInfo)
				actionRecieved((CollisionActionInfo) object, connection);
			else if (object instanceof ProduceInfo)
				produceInfoReceived((ProduceInfo) object);
		}

	}

	private class ServerBindingExcepiton extends GameException
	{
		public ServerBindingExcepiton(String message)
		{
			super(message);
		}
	}

}
