package com.bubbletrouble.game.states;

import com.esotericsoftware.kryonet.Server;

public class PlayServerState extends PlayState
{
	Server server;

	public PlayServerState(Server server)
	{
		this.server = server;
	}
}
