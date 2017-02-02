package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.BubbleTroubleGameServer;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.states.play.PlayClientState;
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
			client.connect(15000, "localhost", BubbleTroubleGameServer.tcpPort, BubbleTroubleGameServer.udpPort);
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
			BubbleTroubleGameClient.states.set(new PlayClientState(client));
	}

	private class ConnectionErrorException extends GameException
	{
		public ConnectionErrorException(String message)
		{
			super("Cannot connect to server : " + message);
		}

	}
}
