package com.bubbletrouble.game.objects.player;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.Action;

import utils.Caster;

@Registerable
public class AddPointsAction implements Action
{
	public int pointsToAdd;

	@Override
	public void makeAction(GameObject gameObject)
	{
		Player player = Caster.cast(gameObject, Player.class);
		player.addPoints(pointsToAdd);
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		makeAction(gameObject);
	}

}
