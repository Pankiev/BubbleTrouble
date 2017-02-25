package com.bubbletrouble.game.objects.obstacle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;

public class Obstacle extends GameObject
{

	public Obstacle(State linkedState, long id)
	{
		super((Texture) ShooterGameClient.assets.get("red.gif"), linkedState);
		setId(id);
	}

	@Override
	public void clientUpdate()
	{
	}

	@Override
	public void serverUpdate()
	{
	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
	}

	@Override
	public ProduceInfo produceInfo()
	{
		ObstacleProduceInfo info = new ObstacleProduceInfo();
		info.x = getX();
		info.y = getY();
		info.id = getId();
		return info;
	}

	@Override
	public int getPointsValue()
	{
		return 100;
	}

}
