package com.bubbletrouble.game.states.play.actions;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.Registerable;

import utils.Caster;

@Registerable
public class MoveRightAction implements Action
{
	private float xChange = 0;

	@Override
	public void makeAction(GameObject gameObject)
	{
		MovableGameObject player = Caster.castToPlayer(gameObject);
		player.moveRight();
		xChange = player.getX();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setX(xChange);
	}
}