package com.bubbletrouble.game.server.packets.player;

import com.bubbletrouble.game.server.packets.Info;

public class PlayerAddInfo extends Info
{
	public PlayerAddInfo()
	{

	}

	public PlayerAddInfo(int id)
	{
		this.id = id;
	}

	public float x = 0;
	public float y = 0;
	public int id = -1;
}
