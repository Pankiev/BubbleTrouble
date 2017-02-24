package com.bubbletrouble.game.packets.produce;

import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.obstacle.Obstacle;

public class ObstacleProduceInfo extends ProduceInfo
{
	public float x;
	public float y;

	@Override
	public GameObject produce(State linkedState)
	{
		Obstacle obstacle = new Obstacle(linkedState, id);
		obstacle.setPosition(x, y);
		return obstacle;
	}
}
