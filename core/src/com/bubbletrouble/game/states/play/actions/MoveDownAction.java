package com.bubbletrouble.game.states.play.actions;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.CollisionAction;
import com.bubbletrouble.game.server.packets.Registerable;

import utils.Caster;

@Registerable
public class MoveDownAction implements CollisionAction
{
	private float yChange = 0;

	@Override
	public void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision)
	{
		MovableGameObject player = Caster.castToPlayer(gameObject);
		player.moveDown(possibleCollision);
		yChange = player.getY();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setY(yChange);
	}
}
