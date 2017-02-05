package com.bubbletrouble.game.server.packets.remove;

import com.bubbletrouble.game.server.packets.Info;

public class ObjectRemoveInfo extends Info
{
	public long id = -1;

	public ObjectRemoveInfo()
	{

	}

	public ObjectRemoveInfo(long id)
	{
		this.id = id;
	}

}
