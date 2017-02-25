package com.bubbletrouble.game.states.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.stringdraw.BitmapStringDrawer;
import com.esotericsoftware.kryonet.Client;

public class PreReconnectionState extends State implements TextInputListener
{
	private Client client;
	BitmapStringDrawer drawer = new BitmapStringDrawer();
	
	public PreReconnectionState(Client client)
	{
		this.client = client;
		Gdx.input.getTextInput(this, "You died, try again?", "y", "");
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawer.draw(batch, "You died, try again?", 20, 20);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void input(String text)
	{
		if (text.equals("y"))
			ShooterGameClient.states.set(new ReconnectionState(client));
	}

	@Override
	public void canceled()
	{
		ShooterGameClient.states.pop();
	}

}
