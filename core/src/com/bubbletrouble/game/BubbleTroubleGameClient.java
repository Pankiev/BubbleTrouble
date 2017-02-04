package com.bubbletrouble.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.Assets;
import com.bubbletrouble.game.libgdxcommon.StateManager;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.CollisionActionInfo;
import com.bubbletrouble.game.server.packets.ObstacleAddInfo;
import com.bubbletrouble.game.server.packets.PacketsRegisterer;
import com.bubbletrouble.game.server.packets.ProduceInfo;
import com.bubbletrouble.game.server.packets.player.PlayerProduceInfo;
import com.bubbletrouble.game.server.packets.player.PlayerRemoveInfo;
import com.bubbletrouble.game.states.connection.ConnectionState;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import utils.Sleeper;

public class BubbleTroubleGameClient extends ApplicationAdapter
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
		registerPackets();
		states.push(new ConnectionState(client));
	}

	private void registerPackets()
	{
		Kryo kryo = client.getKryo();
		PacketsRegisterer.registerAllAnnotated(kryo);
		PacketsRegisterer.registerDefaults(kryo);
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
			if (object instanceof PlayerProduceInfo[])
			{
				PlayerProduceInfo[] playerInfo = (PlayerProduceInfo[]) object;


				playState.addObjects(playerInfo);
			} 
			else if (object instanceof PlayerProduceInfo)
			{
				PlayerProduceInfo playerInfo = (PlayerProduceInfo) object;
				playState.addObject(playerInfo);
			} 
			else if(object instanceof PlayerRemoveInfo)
			{
				PlayerRemoveInfo playerInfo = (PlayerRemoveInfo) object;
				playState.removeObject(playerInfo.id);
			}
			else if (object instanceof ObstacleAddInfo)
			{
				playState.addObstacle((ObstacleAddInfo) object);
				Log.info("Obstacle added");
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
