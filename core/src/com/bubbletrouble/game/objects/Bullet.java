package com.bubbletrouble.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;

public class Bullet extends MovableGameObject
{
	private final Vector2 flyingVector;
	private final float MAX_LIVE_TIME = 2.0f;
	private float livingTime = 0.0f;

	public Bullet(Vector2 mousePosition, Vector2 sourcePosition)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("bullet.png"));
		setPosition(sourcePosition.x, sourcePosition.y);
		flyingVector = sourcePosition.sub(mousePosition);
		setMoveSpeed(6.5f);
	}

	@Override
	public void update()
	{
		livingTime += Gdx.graphics.getDeltaTime();
		float angle = flyingVector.angle();
		flyingVector.set(getMoveSpeed(), getMoveSpeed());
		flyingVector.setAngle(angle);
		setPosition(getX() - flyingVector.x, getY() - flyingVector.y);
		needsPositionUpdate = true;
	}

	@Override
	public boolean shouldBeDeleted()
	{
		return livingTime > MAX_LIVE_TIME;
	};

	@Override
	public ProduceInfo produceInfo()
	{
		//// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented yet");
	}

}
