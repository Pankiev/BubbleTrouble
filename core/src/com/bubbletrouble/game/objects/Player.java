package com.bubbletrouble.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.server.packets.produce.ProduceBulletInfo;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;

public class Player extends MovableGameObject
{
	private static final float ROTATION_CONSTANT = 140.0f;
	Vector2 center = new Vector2();

	public Player(long id)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("blue.gif"));
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
		float rotation = difference.angle() + ROTATION_CONSTANT;
		setRotation(rotation);
	}

	public ProduceBulletInfo produceShootenBulletInfo()
	{
		ProduceBulletInfo info = new ProduceBulletInfo();
		info.x = getX() + 20;
		info.y = getY() + 10;
		info.mouseX = Gdx.input.getX();
		info.mouseY = -Gdx.input.getY() + Gdx.graphics.getHeight();
		return info;
	}

	@Override
	public ProduceInfo produceInfo()
	{
		PlayerProduceInfo info = new PlayerProduceInfo();
		info.x = getX();
		info.y = getY();
		info.id = getId();
		return info;
	}

}
