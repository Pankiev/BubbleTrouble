package com.bubbletrouble.game.server.packets.player;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.ProduceInfo;

public class PlayerAddInfo extends ProduceInfo
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

	@Override
	public GameObject produce()
	{
		Player player = new Player();
		player.setPosition(x, y);
		return player;
	}
}
