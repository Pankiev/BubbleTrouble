package com.bubbletrouble.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.ProduceInfo;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;

public class Player extends MovableGameObject
{
	Vector2 center = new Vector2();

	public Player(long id)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("blue.bmp"));
		setId(id);
	}

	@Override
	public void update()
	{

	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
	}

	public void updateAngle(Vector2 mousePosition)
	{
		center = getSprite().getBoundingRectangle().getCenter(center);
		Vector2 difference = center.sub(mousePosition);
		float rotation = difference.angle();
		setRotation(rotation);
	}

	@Override
	public ProduceInfo produceInfo()
	{
		PlayerAddInfo info = new PlayerAddInfo();
		info.x = getX();
		info.y = getY();
		info.id = getId();
		return info;
	}

}
