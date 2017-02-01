package com.bubbletrouble.game.states;

import com.bubbletrouble.game.objects.Player;
import com.esotericsoftware.kryonet.Client;

public class PlayClientState extends PlayState
{
	Client client;

	public PlayClientState(Client client)
	{
		this.client = client;
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
		player = new Player();
	}
}
