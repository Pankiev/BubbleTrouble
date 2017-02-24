package com.bubbletrouble.game.objects.player;

import java.util.Collection;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.CollisionAction;

import utils.Caster;

@Registerable
public class MoveLeftAction implements CollisionAction
{
	private float xChange = 0;
	private float yChange = 0;

	public void makeAction(GameObject gameObject, Collection<GameObject> collisions)
	{
		Player player = Caster.cast(gameObject, Player.class);
		player.moveLeft(collisions);
		xChange = player.getX();
		yChange = player.getY();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setX(xChange);
		gameObject.setY(yChange);
	}
}
