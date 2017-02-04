package com.bubbletrouble.game.server.packets.player;

import com.bubbletrouble.game.server.packets.Info;

public class PlayerRemoveInfo extends Info
{
	public PlayerRemoveInfo()
	{

	}

	public PlayerRemoveInfo(int id)
	{
		this.id = id;
	}

	public long id = -1;
}
