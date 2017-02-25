package com.bubbletrouble.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGame;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;

public class ExitingState extends State
{
	private int timeToClose;
	private BitmapFont font = ShooterGame.assets.getFont();

	public ExitingState(int timeOut)
	{
		timeToClose = timeOut;
		font.setColor(new Color(0, 0, 0, 0.8f));
	}

	@Override
	public void render(SpriteBatch batch)
	{
		float secondsToClose = (float) timeToClose / 1000.0f;
		String message = String.valueOf(secondsToClose) + " seconds left...";
		font.draw(batch, message, 20, 20);
	}

	@Override
	public void update()
	{
		timeToClose -= Gdx.graphics.getDeltaTime() * 1000;
		if (timeToClose < 0.0f)
			ShooterGameClient.states.pop();

	}

}
