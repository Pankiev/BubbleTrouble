package com.bubbletrouble.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.BubbleTroubleGame;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;

public class Player extends MovableGameObject
{

	public Player()
	{
		super((Texture) BubbleTroubleGame.assets.get("blue.bmp"));
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(lookout, x, y);
	}

}