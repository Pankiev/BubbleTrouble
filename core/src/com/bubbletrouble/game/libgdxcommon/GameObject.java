package com.bubbletrouble.game.libgdxcommon;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject extends Sprite
{
	
	protected GameObject(Texture lookout)
	{
		super();
		super.setTexture(lookout);
		// super.setRegion(lookout);
	}

	public abstract void update();

	public abstract void render(SpriteBatch batch);
}