package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.BubbleTroubleGameServer;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryonet.Client;

public class ConnectionState extends State implements TextInputListener
{
	private Client client;

	public ConnectionState(Client client)
	{
		this.client = client;
		Gdx.input.getTextInput(this, "IpAdress", "localhost", "");
	}

	private void startConnecting(String connectionIp)
	{
		try
		{
			client.connect(15000, connectionIp, BubbleTroubleGameServer.tcpPort, BubbleTroubleGameServer.udpPort);
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

	@Override
	public void input(String ipAdress)
	{
		startConnecting(ipAdress);
	}

	@Override
	public void canceled()
	{
		startConnecting("localhost");
	}
}
