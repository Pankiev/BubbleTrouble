package com.bubbletrouble.game.libgdxcommon;

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class MovableGameObject extends GameObject
{
	private float moveSpeed = 100.0f;

	protected MovableGameObject(Texture lookout)
	{
		super(lookout);
	}

	public GameObject moveRight(Collection<GameObject> possibleCollision)
	{
		moveRight();
		GameObject collision = checkForCollision(possibleCollision);
		if(collision != null)
			moveLeft();
		return collision;
	}

	public GameObject moveLeft(Collection<GameObject> possibleCollision)
	{
		moveLeft();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			moveRight();
		return collision;
	}

	public GameObject moveUp(Collection<GameObject> possibleCollision)
	{
		moveUp();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			moveDown();
		return collision;
	}

	public GameObject moveDown(Collection<GameObject> possibleCollision)
	{
		moveDown();
		GameObject collision = checkForCollision(possibleCollision);
		if (collision != null)
			moveUp();
		return collision;
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
