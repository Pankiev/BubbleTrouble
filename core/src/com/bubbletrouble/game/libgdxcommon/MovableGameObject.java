package com.bubbletrouble.game.libgdxcommon;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class MovableGameObject extends GameObject
{
	private float moveSpeed = 100.0f;

	protected MovableGameObject(Texture lookout, State linkedState)
	{
		super(lookout, linkedState);
	}

	public GameObject moveRight(Collection<GameObject> possibleCollision)
	{
		moveRight();
		GameObject collision = checkForCollision(possibleCollision);
		if(collision != null)
			fixPositionOnCollisionAfterMovingRight(collision);
		return collision;
	}

	private void fixPositionOnCollisionAfterMovingRight(GameObject collision)
	{
		moveLeft();
		if (collision.getY() > getY())
			setY(getY() - getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		else
			setY(getY() + getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
	}

	public GameObject moveLeft(Collection<GameObject> possibleCollision)
	{
		moveLeft();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			fixPositionOnCollisionAfterMovingLeft(collision);
		return collision;
	}

	private void fixPositionOnCollisionAfterMovingLeft(GameObject collision)
	{
		moveRight();
		if (collision.getY() > getY())
			setY(getY() - getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		else
			setY(getY() + getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
	}

	public GameObject moveUp(Collection<GameObject> possibleCollision)
	{
		moveUp();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			fixPositionOnCollisionAfterMovingUp(collision);
		return collision;
	}

	private void fixPositionOnCollisionAfterMovingUp(GameObject collision)
	{
		moveDown();
		if (collision.getX() > getX())
			setX(getX() - getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		else
			setX(getX() + getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
	}

	public GameObject moveDown(Collection<GameObject> possibleCollision)
	{
		moveDown();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			fixPositionOnCollisionAfterMovingDown(collision);
		return collision;
	}

	private void fixPositionOnCollisionAfterMovingDown(GameObject collision)
	{
		moveUp();
		if (collision.getX() > getX())
			setX(getX() - getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
		else
			setX(getX() + getMoveSpeed() * Gdx.graphics.getDeltaTime() / 6.0f);
	}

	public void moveLeft()
	{
		setX(getX() - getMoveSpeed() * Gdx.graphics.getDeltaTime());
	}

	public void moveRight()
	{
		setX(getX() + getMoveSpeed() * Gdx.graphics.getDeltaTime());
	}

	public void moveDown()
	{
		setY(getY() - getMoveSpeed() * Gdx.graphics.getDeltaTime());
	}

	public void moveUp()
	{
		setY(getY() + getMoveSpeed() * Gdx.graphics.getDeltaTime());
	}

	public float getMoveSpeed()
	{
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed)
	{
		this.moveSpeed = moveSpeed;
	}

}
