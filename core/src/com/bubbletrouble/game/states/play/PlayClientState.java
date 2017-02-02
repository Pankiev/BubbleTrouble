package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.server.packets.PlayerAddInfo;
import com.esotericsoftware.kryonet.Client;

public class PlayClientState extends PlayState
{
	Client client;

	public PlayClientState(Client client)
	{
		this.client = client;
		addPlayer(new PlayerAddInfo(client.getID()));
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
	}

	@Override
	public void update()
	{
	}
}
