package com.bubbletrouble.game.libgdxcommon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.input.InputProcessorAdapter;

public abstract class State
{
	protected OrthographicCamera camera = new OrthographicCamera();
	protected InputProcessorAdapter inputHandler;

	public abstract void render(SpriteBatch batch);

	public abstract void update();

	public void activateInputHandler()
	{
		Gdx.input.setInputProcessor(inputHandler);
	}
}
