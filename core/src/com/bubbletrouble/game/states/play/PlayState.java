package com.bubbletrouble.game.states.play;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;

public abstract class PlayState extends State
{
	Map<Integer, Player> players = new TreeMap<>();

	@Override
	public void render(SpriteBatch batch)
	{
		for (Player player : players.values())
			player.render(batch);
	}

	@Override
	public void update()
	{
		for (Player player : players.values())
			player.update();
	}
}
