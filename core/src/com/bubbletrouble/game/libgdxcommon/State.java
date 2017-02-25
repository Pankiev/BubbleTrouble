package com.bubbletrouble.game.libgdxcommon;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.camerashake.CameraShaker;
import com.bubbletrouble.game.libgdxcommon.camerashake.DefaultCameraShaker;
import com.bubbletrouble.game.libgdxcommon.input.InputProcessorAdapter;

public abstract class State
{
	protected OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	protected InputProcessorAdapter inputHandler;

	protected Collection<CameraShaker> cameraShakers = new LinkedList<>();

	public State()
	{
		camera.setToOrtho(false);
	}

	public abstract void render(SpriteBatch batch);

	public abstract void update();

	public void activateInputHandler()
	{
		Gdx.input.setInputProcessor(inputHandler);
	}

	public OrthographicCamera getCamera()
	{
		return camera;
	}

	public void shakeCamera(DefaultCameraShaker shaker)
	{
		cameraShakers.add(shaker);
	}

	protected void shakeHandle()
	{
		Iterator<CameraShaker> it = cameraShakers.iterator();
		while (it.hasNext())
		{
			CameraShaker shaker = it.next();
			shaker.shake();
			if (shaker.hasFinished())
				it.remove();
		}
		camera.update();
	}
}
