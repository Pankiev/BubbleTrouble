package com.bubbletrouble.game.libgdxcommon.stringdraw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface StringDrawer
{
	void draw(SpriteBatch batch, String text, int x, int y);

	void setColor(Color color);

	default void draw(SpriteBatch batch, String text, float x, float y)
	{
		draw(batch, text, (int) x, (int) y);
	}
}
