package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.BubbleTroubleGameServer;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryonet.Client;

public class ConnectionState extends State implements TextInputListener
{
	private Client client;
	BitmapFont font = new BitmapFont();
	private static final float timeout = 10.0f;
	float connectingTime = 0.0f;
	String messageToUser = "";

	public ConnectionState(Client client)
	{
		this.client = client;
		font.setColor(new Color(0, 0, 0, 0.8f));
		askForIp();
	}

	private void askForIp()
	{
		Gdx.input.getTextInput(this, "IpAdress", "localhost", "");
	}

	private void tryConnecting(String connectionIp)
	{
		messageToUser = "Connecting";
		try
		{
			connect(connectionIp);
		} catch (IOException e)
		{
			connectionFailed(connectionIp);
		}
	}

	private void connect(String connectionIp) throws IOException
	{
		client.connect(15000, connectionIp, BubbleTroubleGameServer.tcpPort, BubbleTroubleGameServer.udpPort);
	}

	private void connectionFailed(String connectionIp)
	{
		if (connectingTime >= timeout)
			onConnectionTimeout();
		else
			tryConnecting(connectionIp);
	}

	private void onConnectionTimeout()
	{
		messageToUser = "Couldn't connect, please check ip adress";
		askForIp();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, messageToUser, 20, 20);
	}

	@Override
	public void update()
	{
		if (client.isConnected())
			BubbleTroubleGameClient.states.set(new PlayClientState(client));
		connectingTime += Gdx.graphics.getDeltaTime();
	}


	@Override
	public void input(String ipAdress)
	{
		tryConnecting(ipAdress);
	}

	@Override
	public void canceled()
	{
		BubbleTroubleGameClient.states.pop();
	}
}
