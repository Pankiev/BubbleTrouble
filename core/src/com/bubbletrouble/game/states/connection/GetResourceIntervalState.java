package com.bubbletrouble.game.states.connection;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;

public class GetResourceIntervalState extends State
{
	private BitmapFont font = new BitmapFont();

	public GetResourceIntervalState()
	{
		font.setColor(0, 0, 0, 0.8f);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, "Retrieving info from server...", 20, 20);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

}
