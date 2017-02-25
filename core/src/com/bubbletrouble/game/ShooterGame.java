package com.bubbletrouble.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.StateManager;

public class ShooterGame extends ApplicationAdapter
{
	protected SpriteBatch batch;
	public static Assets assets;
	public static StateManager states;


	@Override
	public void create()
	{
		batch = new SpriteBatch();
		assets = new Assets();
		states = new StateManager();
	}

	@Override
	public void render()
	{
		update();
		clearScreen();
		actualRender();
	}

	private void actualRender()
	{
		Camera usedCamera = states.usedState().getCamera();
		batch.setProjectionMatrix(usedCamera.combined);
		batch.begin();
		states.render(batch);
		batch.end();
	}

	private void update()
	{
		states.update();
	}

	private void clearScreen()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		assets.dispose();
	}
}
