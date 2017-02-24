package com.bubbletrouble.game.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceBulletInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.play.PlayState;

public class Player extends MovableGameObject
{
	private static final float ROTATION_CONSTANT = 140.0f;
	private Vector2 center = new Vector2();
	private float shootingTime = 0.0f;
	private float shootingInterval = 0.3f;

	public Player(PlayState linkedState, long id)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("blue.gif"), linkedState);
		setId(id);
	}

	@Override
	public void update()
	{
		shootingTime += Gdx.graphics.getDeltaTime();
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

	public ProduceBulletInfo produceShootenBulletInfo(float mouseX, float mouseY)
	{
		ProduceBulletInfo info = new ProduceBulletInfo();
		info.mouseX = mouseX;
		info.mouseY = mouseY;
		center = getSprite().getBoundingRectangle().getCenter(center);

		Vector2 circularPosition = new Vector2(getX() - mouseX, getY() - mouseY);
		float angle = circularPosition.angle();
		circularPosition.set(15.0f, 15.0f);
		circularPosition.setAngle(angle);

		info.x = center.x - 7.5f - circularPosition.x;
		info.y = center.y - 3.5f - circularPosition.y;
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

	public boolean canShoot()
	{
		if (shootingTime > shootingInterval)
		{
			shootingTime = 0.0f;
			return true;
		}
		return false;
	}

}
