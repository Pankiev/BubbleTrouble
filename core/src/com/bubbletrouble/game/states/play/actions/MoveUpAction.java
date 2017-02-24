package com.bubbletrouble.game.states.play.actions;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Registerable;
import com.bubbletrouble.game.server.packets.action.CollisionAction;

import utils.Caster;

@Registerable
public class MoveUpAction implements CollisionAction
{
	private float yChange;
	private float xChange = 0;

	@Override
	public void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision)
	{
		MovableGameObject player = Caster.castToPlayer(gameObject, Player.class);
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