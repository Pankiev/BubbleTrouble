package com.bubbletrouble.game.packets.produce;

import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.states.play.PlayState;

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
	public GameObject produce(State linkedState)
	{
		Player player = new Player((PlayState) linkedState, id);
		player.setPosition(x, y);
		return player;
	}
}
