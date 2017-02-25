package com.bubbletrouble.game.states.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGame;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.esotericsoftware.kryonet.Client;

public class PreReconnectionState extends State implements TextInputListener
{
	private Client client;
	private BitmapFont font = ShooterGame.assets.getFont();
	private ConnectionData data;
	
	public PreReconnectionState(Client client, ConnectionData data)
	{
		this.client = client;
		this.data = data;
		Gdx.input.getTextInput(this, "You died, try again?", "y", "");
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, "You died, try again?", 20, 20);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void input(String text)
	{
		if (text.equals("y"))
			ShooterGameClient.states.set(new ReconnectionState(client, data));
	}

	@Override
	public void canceled()
	{
		ShooterGameClient.states.pop();
	}

}
