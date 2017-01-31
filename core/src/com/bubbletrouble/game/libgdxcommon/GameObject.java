package com.bubbletrouble.game.libgdxcommon;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject extends Rectangle
{
	protected Texture lookout;
	
	protected GameObject(Texture lookout)
	{
		super();
		this.lookout = lookout;
		setSizeFromTexture();
	}

	public abstract void update();

	public abstract void render(SpriteBatch batch);
	
	public void setSizeFromTexture()
	{
		this.width = lookout.getWidth();
		this.height = lookout.getHeight();
	}

}