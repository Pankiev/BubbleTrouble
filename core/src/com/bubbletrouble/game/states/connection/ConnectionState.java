package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.ShooterGameServer;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.stringdraw.BitmapStringDrawer;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryonet.Client;

import utils.Sleeper;

public class ConnectionState extends State implements TextInputListener
{
	private Client client;
	private static final float TIMEOUT = 10.0f;
	private float connectingTime = 0.0f;
	private String messageToUser = "";
	private boolean isConnecting = false;
	private BitmapStringDrawer drawer = new BitmapStringDrawer();
	private ConnectionData data;

	public ConnectionState(Client client, ConnectionData data)
	{
		this.client = client;
		this.data = data;
		drawer.setColor(new Color(0, 0, 0, 0.8f));
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
            isConnecting = true;
			connect(connectionIp);
		} catch (IOException e)
		{
			connectionFailed(connectionIp);
		}
	}

	private void connect(String connectionIp) throws IOException
	{
		client.connect(15000, connectionIp, ShooterGameServer.tcpPort, ShooterGameServer.udpPort);
	}

	private void connectionFailed(String connectionIp)
	{
        Sleeper.sleep(1000);
        connectingTime += Gdx.graphics.getDeltaTime() * 100;
		if (connectingTime >= TIMEOUT)
			onConnectionTimeout();
        else
            tryConnecting(connectionIp);
	}

	private void onConnectionTimeout()
	{
		messageToUser = "Couldn't connect, please check ip adress";
		connectingTime = 0.0f;
        isConnecting = false;
		askForIp();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawer.draw(batch, messageToUser, 20, 20);
	}

	@Override
	public void update()
	{
		if (client.isConnected())
			ShooterGameClient.states.set(new PlayClientState(client, data));
        if(isConnecting)
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
		askForIp();
	}
}
