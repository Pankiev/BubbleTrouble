package com.bubbletrouble.game.server.packets;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Obstacle;

public class ObstacleAddInfo extends ProduceInfo
{
	public float x;
	public float y;

	@Override
	public GameObject produce()
	{
		Obstacle obstacle = new Obstacle(id);
		obstacle.setPosition(x, y);
		return obstacle;
	}
}
