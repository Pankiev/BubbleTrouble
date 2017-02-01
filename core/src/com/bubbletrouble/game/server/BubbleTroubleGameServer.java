package com.bubbletrouble.game.server;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.server.listener.ServerListener;
import com.bubbletrouble.game.server.packets.PacketsRegisterer;
import com.bubbletrouble.game.states.PlayServerState;
import com.bubbletrouble.game.states.PlayState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class BubbleTroubleGameServer extends ApplicationAdapter
{
	public static final int tcpPort = 8000;
	public static final int udpPort = 8001;

	boolean isRunning = true;
	Server server;
	PlayState playState;

	@Override
	public void create()
	{
		BubbleTroubleGameClient.initialize();
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

	private class ServerBindingExcepiton extends GameException
	{
		public ServerBindingExcepiton(String message)
		{
			super(message);
		}
	}

}
