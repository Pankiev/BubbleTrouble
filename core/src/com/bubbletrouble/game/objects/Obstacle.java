package com.bubbletrouble.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.ObstacleAddInfo;
import com.bubbletrouble.game.server.packets.ProduceInfo;

public class Obstacle extends GameObject
{

	public Obstacle(long id)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("red.gif"));
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
		ObstacleAddInfo info = new ObstacleAddInfo();
		info.x = getX();
		info.y = getY();
		info.id = getId();
		return info;
	}

}
