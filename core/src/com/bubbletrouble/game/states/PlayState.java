package com.bubbletrouble.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;

public class PlayState extends State
{
	public Player player;

	public PlayState()
	{
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
		player = new Player();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		player.render(batch);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

}
