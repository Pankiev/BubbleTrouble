package com.bubbletrouble.game.objects.bullet;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.server.packets.action.Action;

public class BulletFlightAction implements Action
{
	public float xChange;
	public float yChange;

	@Override
	public void makeAction(GameObject gameObject)
	{
		Bullet bullet = (Bullet) gameObject;
		bullet.updatePosition();
		xChange = bullet.getX();
		yChange = bullet.getY();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setX(xChange);
		gameObject.setY(yChange);
	}

}
