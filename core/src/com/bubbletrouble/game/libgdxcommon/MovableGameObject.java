package com.bubbletrouble.game.libgdxcommon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class MovableGameObject extends GameObject
{
	private float moveSpeed = 100.0f;

	protected MovableGameObject(Texture lookout)
	{
		super(lookout);
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
