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
		x -= getMoveSpeed() * Gdx.graphics.getDeltaTime();
	}

	public void moveRight()
	{
		x += getMoveSpeed() * Gdx.graphics.getDeltaTime();
	}

	public void moveDown()
	{
		y -= getMoveSpeed() * Gdx.graphics.getDeltaTime();
	}

	public void moveUp()
	{
		y += getMoveSpeed() * Gdx.graphics.getDeltaTime();
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
