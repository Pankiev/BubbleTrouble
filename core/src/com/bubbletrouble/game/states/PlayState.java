package com.bubbletrouble.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;

public abstract class PlayState extends State
{
	protected Player player = new Player();

	@Override
	public void render(SpriteBatch batch)
	{
		player.render(batch);
	}

	@Override
	public void update()
	{
		player.update();
	}
}
