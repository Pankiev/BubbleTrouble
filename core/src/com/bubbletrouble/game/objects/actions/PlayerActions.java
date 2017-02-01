package com.bubbletrouble.game.objects.actions;

import com.bubbletrouble.game.libgdxcommon.BadTypeException;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.communication.action.Action;
import com.bubbletrouble.game.objects.Player;

public class PlayerActions
{
	public static Action moveLeft = (gameObject) ->
	{
		Player player = castToPlayer(gameObject);
		player.moveLeft();
	};

	public static Action moveRight = (gameObject) ->
	{
		Player player = castToPlayer(gameObject);
		player.moveRight();
	};

	public static Action moveDown = (gameObject) ->
	{
		Player player = castToPlayer(gameObject);
		player.moveDown();
	};

	public static Action moveUp = (gameObject) ->
	{
		Player player = castToPlayer(gameObject);
		player.moveUp();
	};

	private static Player castToPlayer(GameObject gameObject)
	{
		if (!(gameObject instanceof Player))
			throw new BadTypeException(Player.class.getName());
		Player player = (Player) gameObject;
		return player;
	}

}
