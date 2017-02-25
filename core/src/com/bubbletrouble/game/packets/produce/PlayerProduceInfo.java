package com.bubbletrouble.game.packets.produce;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.states.play.PlayState;

@Registerable
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
	public int points;
	
	@Override
	public GameObject produce(State linkedState)
	{
		Player player = new Player((PlayState) linkedState, id);
		player.setPosition(x, y);
		player.setPoints(points);
		return player;
	}
}
