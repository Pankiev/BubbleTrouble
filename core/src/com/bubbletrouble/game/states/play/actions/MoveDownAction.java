package com.bubbletrouble.game.states.play.actions;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.MovableGameObject;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.Registerable;

import utils.Caster;

@Registerable
public class MoveDownAction implements Action
{
	private float yChange = 0;
	@Override
	public void makeAction(GameObject gameObject)
	{
		MovableGameObject player = Caster.castToPlayer(gameObject);
		player.moveDown();
		yChange = player.getY();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setY(yChange);
	}
}