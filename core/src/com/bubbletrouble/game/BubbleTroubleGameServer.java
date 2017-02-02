package com.bubbletrouble.game;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.Info;
import com.bubbletrouble.game.server.packets.PacketsRegisterer;
import com.bubbletrouble.game.server.packets.PlayerInfo;
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
		PacketsRegisterer.registerAllSubtypes(kryo, Info.class);
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
			Integer id = connection.getID();
			server.sendToTCP(id, playState.getPlayersInfo());
			server.sendToAllExceptTCP(id, new PlayerInfo(id));
			playState.addPlayer(new PlayerInfo(connection.getID()));
			Log.info(">> Player added " + connection.getID());
		}

		@Override
		public void disconnected(Connection connection)
		{
			server.sendToAllExceptTCP(connection.getID(), "Player remove, not implemented yet");
			playState.removePlayer(connection.getID());
			Log.info(">> Player removed " + connection.getID());
		}

		@Override
		public void received(Connection connection, Object object)
		{
			if (object instanceof ActionInfo)
				actionRecieved((ActionInfo) object, connection);
			// Log.info("action received : " + object);
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
