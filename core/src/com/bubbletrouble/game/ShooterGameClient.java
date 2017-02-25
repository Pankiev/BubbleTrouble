package com.bubbletrouble.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.kryonetcommon.PacketsRegisterer;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.StateManager;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.packets.remove.ObjectRemoveInfo;
import com.bubbletrouble.game.states.connection.ConnectionState;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import utils.Sleeper;

public class ShooterGameClient extends ApplicationAdapter
{
	SpriteBatch batch;
	Client client = new Client();
	public static Assets assets;
	public static StateManager states;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		assets = new Assets();
		states = new StateManager();
		client = new Client();
		client.start();
		client.addListener(new ClientListener());
		PacketsRegisterer.registerPackets(client.getKryo());
		states.push(new ConnectionState(client));
	}

	@Override
	public void render()
	{
		clearScreen();
		actualRender();
		update();
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

	private PlayClientState findPlayState()
	{
		return states.findPlayState();
	}

	private void actionRecieved(ActionInfo actionInfo)
	{
		PlayClientState playState = findPlayState();
		if (playState != null)
			playState.applyChanges(actionInfo);

	}

	private void actionRecieved(CollisionActionInfo actionInfo)
	{
		PlayClientState playState = findPlayState();
		playState.applyChanges(actionInfo);
	}

	private class ClientListener extends Listener
	{
		@Override
		public void received(Connection connection, Object object)
		{
			PlayClientState playState = findPlayState();
			while (playState == null)
			{
				Sleeper.sleep(10);
				playState = findPlayState();
			}

			if (object instanceof ObjectRemoveInfo)
			{
				ObjectRemoveInfo playerInfo = (ObjectRemoveInfo) object;
				playState.removeObject(playerInfo.id);
			}
			else if(object instanceof ProduceInfo)
			{
				ProduceInfo produceInfo = (ProduceInfo) object;
				playState.addObject(produceInfo);
			}
			else if(object instanceof ProduceInfo[])
			{
				ProduceInfo[] produceInfo = (ProduceInfo[]) object;
				playState.addObjects(produceInfo);
			}
			else if (object instanceof ActionInfo)
				actionRecieved((ActionInfo) object);
			else if (object instanceof CollisionActionInfo)
				actionRecieved((CollisionActionInfo) object);
		}

	}
}
