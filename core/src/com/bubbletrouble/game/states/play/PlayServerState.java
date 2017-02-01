package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Server;

public class PlayServerState extends PlayState
{
	Server server;

	public PlayServerState(Server server)
	{
		this.server = server;
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void render(SpriteBatch batch)
	{
	}
}
