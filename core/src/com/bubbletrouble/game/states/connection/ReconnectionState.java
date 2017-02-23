package com.bubbletrouble.game.states.connection;

import java.io.IOException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryonet.Client;

public class ReconnectionState extends State
{
	private Client client;
	private BitmapFont font = BubbleTroubleGameClient.assets.getFont();

	public ReconnectionState(Client client)
	{
		this.client = client;
		font.setColor(new Color(0, 0, 0, 0.8f));
	}

	private void tryReconnecting()
	{
		try
		{
			client.reconnect();
		} catch (IOException e)
		{
		}
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, "Reconnecting...", 20, 20);
	}

	@Override
	public void update()
	{
		tryReconnecting();
		if (client.isConnected())
			BubbleTroubleGameClient.states.set(new PlayClientState(client));
	}

}
