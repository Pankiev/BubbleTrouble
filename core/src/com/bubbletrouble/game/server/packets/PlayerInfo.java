package com.bubbletrouble.game.server.packets;

//@Registerable
public class PlayerInfo extends Info
{
	public PlayerInfo()
	{

	}

	public PlayerInfo(int id)
	{
		this.id = id;
	}

	public float x = 0;
	public float y = 0;
	public int id = -1;
}
