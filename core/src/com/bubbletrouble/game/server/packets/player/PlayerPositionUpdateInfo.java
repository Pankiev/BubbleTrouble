package com.bubbletrouble.game.server.packets.player;

import com.bubbletrouble.game.server.packets.Info;

public class PlayerPositionUpdateInfo extends Info
{
	public float x = -1;
	public float y = -1;
	public int id = -1;

	public PlayerPositionUpdateInfo()
	{

	}

	public PlayerPositionUpdateInfo(int id)
	{
		this.id = id;
	}


}
