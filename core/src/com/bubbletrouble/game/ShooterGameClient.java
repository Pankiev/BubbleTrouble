package com.bubbletrouble.game;

import java.util.Collection;
import java.util.LinkedList;

import com.bubbletrouble.game.kryonetcommon.PacketsRegisterer;
import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.packets.action.ActionInfo;
import com.bubbletrouble.game.packets.action.CollisionActionInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.packets.remove.ObjectRemoveInfo;
import com.bubbletrouble.game.states.connection.PreConnectionState;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import utils.Sleeper;
import utils.Sleeper.TimeoutException;

public class ShooterGameClient extends ShooterGame
{
	private final Client client = new Client();
	private final Collection<Object> unhandledPackets = new LinkedList<>();

	@Override
	public void create()
	{
		super.create();
		client.start();
		client.addListener(new ClientListener());
		registerPackets(client.getKryo());
		states.push(new PreConnectionState(client));
	}

	private void registerPackets(Kryo kryo)
	{
		PacketsRegisterer.registerAllAnnotated(kryo, Registerable.class, "com.bubbletrouble.game.objects");
		PacketsRegisterer.registerAllAnnotated(kryo, Registerable.class, "com.bubbletrouble.game.packets");
		PacketsRegisterer.registerDefaults(kryo);
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
			PlayClientState playState;
			try
			{
				playState = Sleeper.sleepUntillReceiveOtherThanNull(() -> findPlayState(), 1000);
			} catch (TimeoutException exception)
			{
				System.out.println("unhandledPacket: " + object);
				unhandledPackets.add(object);
				return;
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
