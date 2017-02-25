package com.bubbletrouble.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.stringdraw.BitmapStringDrawer;

public class ExitingState extends State
{
	private int timeToClose;
	BitmapStringDrawer drawer = new BitmapStringDrawer();

	public ExitingState(int timeOut)
	{
		timeToClose = timeOut;
		drawer.setColor(new Color(0, 0, 0, 0.8f));
	}

	@Override
	public void render(SpriteBatch batch)
	{
		float secondsToClose = (float) timeToClose / 1000.0f;
		String message = String.valueOf(secondsToClose) + " seconds left...";
		drawer.draw(batch, message, 20, 20);
	}

	@Override
	public void update()
	{
		timeToClose -= Gdx.graphics.getDeltaTime() * 1000;
		if (timeToClose < 0.0f)
		{
			ShooterGameClient.states.pop();
		}
	}

}
