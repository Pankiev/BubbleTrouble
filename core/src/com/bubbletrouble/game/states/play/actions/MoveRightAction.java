package com.bubbletrouble.game.states.play.actions;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.Registerable;

import utils.Caster;

@Registerable
public class MoveRightAction implements Action
{
	@Override
	public void makeAction(GameObject gameObject)
	{
		Player player = Caster.castToPlayer(gameObject);
		player.moveRight();
	}
}