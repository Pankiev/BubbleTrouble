package com.bubbletrouble.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.GameException;
import com.bubbletrouble.game.libgdxcommon.InputProcessorAdapter;
import com.bubbletrouble.game.libgdxcommon.StateManager;
import com.bubbletrouble.game.states.ConnectionState;
import com.bubbletrouble.game.states.PlayClientState;
import com.esotericsoftware.kryonet.Client;

public class BubbleTroubleGameClient extends ApplicationAdapter
{
	SpriteBatch batch;
	Client client = new Client();
	public static Assets assets;
	public static StateManager states;

	public static void initialize()
	{
		assets = new Assets();
		states = new StateManager();
	}

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		initialize();
		client = new Client();
		states.push(new PlayClientState(client));
		states.push(new ConnectionState(client));
	}

	@Override
	public void render()
	{
		handleInput();
		clearScreen();
		actualRender();
	}

	private void handleInput()
	{
		InputProcessorAdapter inputHandler = getInputProcessor();
		inputHandler.process();
	}

	private InputProcessorAdapter getInputProcessor()
	{
		InputProcessor inputHandler = Gdx.input.getInputProcessor();
		if (!(inputHandler instanceof InputProcessorAdapter))
			throw new BadInputHandlerException(inputHandler.getClass());
		return (InputProcessorAdapter) inputHandler;
	}

	private void clearScreen()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void actualRender()
	{
		batch.begin();
		states.render(batch);
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		assets.dispose();
	}

	private class BadInputHandlerException extends GameException
	{
		public BadInputHandlerException(Class<?> badType)
		{
			super("An instance of " + badType.getName() + "expected");
		}
	}
}
