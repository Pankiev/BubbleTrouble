package com.bubbletrouble.game.objects.player;

import java.util.Collection;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.packets.action.CollisionAction;

import utils.Caster;

@Registerable
public class MoveUpAction implements CollisionAction
{
	private float yChange;
	private float xChange = 0;

	@Override
	public void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision)
	{
		MovableGameObject player = Caster.cast(gameObject, Player.class);
		player.moveUp(possibleCollision);
		yChange = player.getY();
		xChange = player.getX();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setY(yChange);
		gameObject.setX(xChange);
	}
}