package com.bubbletrouble.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.server.packets.produce.ObstacleProduceInfo;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;

public class Obstacle extends GameObject
{

	public Obstacle(State linkedState, long id)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("red.gif"), linkedState);
		setId(id);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

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

}
