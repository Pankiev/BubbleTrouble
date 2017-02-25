package com.bubbletrouble.game.states.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.stringdraw.BitmapStringDrawer;
import com.esotericsoftware.kryonet.Client;

public class PreConnectionState extends State implements TextInputListener
{
	private Client client;
	ConnectionData data = new ConnectionData();
	BitmapStringDrawer drawer = new BitmapStringDrawer();

	public PreConnectionState(Client client)
	{
		this.client = client;
		Gdx.input.getTextInput(this, "Your nickname", "unnamed", "");
	}

	@Override
	public void render(SpriteBatch batch)
	{
		drawer.setColor(Color.GREEN);
		drawer.draw(batch, "Please fill to connect", 20, 20);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void input(String nickname)
	{
		data.setNickname(nickname);
		ShooterGameClient.states.set(new ConnectionState(client, data));
	}

	@Override
	public void canceled()
	{
		ShooterGameClient.states.pop();
	}

}
