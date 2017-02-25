package com.bubbletrouble.game.libgdxcommon.stringdraw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BitmapStringDrawer implements StringDrawer
{
	private static BitmapFont font;
	
	static
	{
		font = new BitmapFont();
	}

	@Override
	public void draw(SpriteBatch batch, String text, int x, int y)
	{
		font.draw(batch, text, x, y);
	}

	@Override
	public void setColor(Color color)
	{
		font.setColor(color);
	}

}
