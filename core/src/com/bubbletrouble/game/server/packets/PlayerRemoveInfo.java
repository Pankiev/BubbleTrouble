package com.bubbletrouble.game.server.packets;

public class PlayerRemoveInfo extends Info
{
	public PlayerRemoveInfo()
	{

	}

	public PlayerRemoveInfo(int id)
	{
		this.id = id;
	}
	public int id = -1;
}
