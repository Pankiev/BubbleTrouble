package com.bubbletrouble.game.server.packets.player;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.ProduceInfo;

public class PlayerProduceInfo extends ProduceInfo
{
	public PlayerProduceInfo()
	{

	}

	public PlayerProduceInfo(int id)
	{
		this.id = id;
	}

	public float x = 0;
	public float y = 0;
	
	@Override
	public GameObject produce()
	{
		Player player = new Player(id);
		player.setPosition(x, y);
		return player;
	}
}