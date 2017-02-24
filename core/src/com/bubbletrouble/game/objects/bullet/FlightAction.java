package com.bubbletrouble.game.objects.bullet;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.Action;

import utils.Caster;

@Registerable
public class FlightAction implements Action
{
	private float xChange;
	private float yChange;

	@Override
	public void makeAction(GameObject gameObject)
	{
		Bullet bullet = Caster.cast(gameObject, Bullet.class);
		bullet.updatePosition();
		xChange = bullet.getX();
		yChange = bullet.getY();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setPosition(xChange, yChange);
	}

}
