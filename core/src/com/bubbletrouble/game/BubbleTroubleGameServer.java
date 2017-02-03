package com.bubbletrouble.game;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.ObstacleAddInfo;
import com.bubbletrouble.game.server.packets.PacketsRegisterer;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;
import com.bubbletrouble.game.server.packets.player.PlayerRemoveInfo;
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
	private int nextObjectId = Integer.MIN_VALUE;

	@Override
	public void create()
	{
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
		update();
	}

	private void update()
	{
		playState.update();
	}

	private void userConnected(Connection connection)
	{
		Integer id = connection.getID();
		server.sendToTCP(id, playState.getPlayersInfo());
		server.sendToTCP(id, playState.getGameObjectsWithoutPlayersInfo());
		server.sendToAllExceptTCP(id, new PlayerAddInfo(id));
		playState.addPlayer(new PlayerAddInfo(connection.getID()));
		Log.info(">> Player added " + connection.getID());

		addRandomObstacle();
	}

	private void addRandomObstacle()
	{
		ObstacleAddInfo addObstacle = new ObstacleAddInfo();
		addObstacle.x = new Random().nextInt(400);
		addObstacle.y = new Random().nextInt(400);
		addObstacle.id = getNextId();
		server.sendToAllTCP(addObstacle);
		playState.addObject(addObstacle);
	}

	private int getNextId()
	{
		nextObjectId++;
		return nextObjectId;
	}

	private void userDisconnected(Connection connection)
	{
		PlayerRemoveInfo removePlayer = new PlayerRemoveInfo(connection.getID());
		server.sendToAllExceptTCP(connection.getID(), removePlayer);
		playState.removePlayer(removePlayer);
		Log.info(">> Player removed " + connection.getID());
	}

	private void actionRecieved(ActionInfo actionInfo, Connection source)
	{
		playState.makeAction(actionInfo);
		server.sendToAllTCP(actionInfo);
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
