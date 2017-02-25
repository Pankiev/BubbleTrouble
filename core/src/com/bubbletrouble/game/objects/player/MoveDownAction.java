package com.bubbletrouble.game.objects.player;

import java.util.Collection;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.packets.action.CollisionAction;

import utils.Caster;

@Registerable
public class MoveDownAction implements CollisionAction
{
	private float yChange = 0;
	private float xChange = 0;

	@Override
	public void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision)
	{
		MovableGameObject player = Caster.cast(gameObject, Player.class);
		player.moveDown(possibleCollision);
		yChange = player.getY();
		xChange = player.getX();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setX(xChange);
		gameObject.setY(yChange);
	}
}
