package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.server.BubbleTroubleGameServer;
import com.esotericsoftware.kryonet.Client;

public class ConnectionState extends State
{
	private Client client;

	public ConnectionState(Client client)
	{
		this.client = client;
		startConnecting();
	}

	private void startConnecting()
	{
		try
		{
			client.start();
			client.connect(5000, "localhost", BubbleTroubleGameServer.tcpPort, BubbleTroubleGameServer.udpPort);
		} catch (IOException e)
		{
			throw new ConnectionErrorException(e.getMessage());
		}
		
	}

	@Override
	public void render(SpriteBatch batch)
	{
	}

	@Override
	public void update()
	{
		if (client.isConnected())
			BubbleTroubleGameClient.states.pop();
	}

	private class ConnectionErrorException extends GameException
	{

		protected ConnectionErrorException()
		{
			super("Cannot connect to server");
		}

		protected ConnectionErrorException(String message)
		{
			super("Cannot connect to server : " + message);
		}

	}
}
