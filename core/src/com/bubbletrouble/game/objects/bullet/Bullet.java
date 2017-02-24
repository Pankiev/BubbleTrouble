package com.bubbletrouble.game.objects.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.packets.produce.ProduceBulletInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.play.PlayState;

public class Bullet extends MovableGameObject
{
	private Vector2 flyingVector;
	private final float MAX_LIVE_TIME = 2.0f;
	private float livingTime = 0.0f;
	private Vector2 mousePosition;

	public Bullet(State linkedState, Vector2 mousePosition, Vector2 sourcePosition)
	{
		super((Texture) BubbleTroubleGameClient.assets.get("bullet.png"), linkedState);
		this.mousePosition = mousePosition;
		setPosition(sourcePosition.x, sourcePosition.y);
		setMoveSpeed(6.5f);
		flyingVector = sourcePosition.sub(mousePosition);
		setPictureRotation();
	}

	private void setPictureRotation()
	{
		float angle = flyingVector.angle();
		setRotation(angle + 180);
	}

	@Override
	public void update()
	{
		updateLivingTime();
		updatePosition();
		needsPositionUpdate = true;
		handlePossibleCollision();
		if (shouldBeDeleted())
			removeFromPlayState(this);
	}

	private void removeFromPlayState(GameObject object)
	{
		((PlayState) linkedState).addToGarbage(object);
	}

	private void handlePossibleCollision()
	{
		GameObject collision = checkForCollision(((PlayState) linkedState).getGameObjects());
		if (collision != null)
			handleCollision(collision);
	}

	private void handleCollision(GameObject collision)
	{
		removeFromPlayState(this);
		removeFromPlayState(collision);
	}


	private void updateLivingTime()
	{
		livingTime += Gdx.graphics.getDeltaTime();
	}

	void updatePosition()
	{
		float angle = flyingVector.angle();
		flyingVector.set(getMoveSpeed(), getMoveSpeed());
		flyingVector.setAngle(angle);
		setRotation(angle);
		setPosition(getX() - flyingVector.x, getY() - flyingVector.y);
	}

	private boolean shouldBeDeleted()
	{
		return livingTime > MAX_LIVE_TIME;
	}

	@Override
	public ProduceInfo produceInfo()
	{
		ProduceBulletInfo info = new ProduceBulletInfo();
		info.id = getId();
		info.mouseX = mousePosition.x;
		info.mouseY = mousePosition.y;
		info.x = getX();
		info.y = getY();
		return info;
	}

}
