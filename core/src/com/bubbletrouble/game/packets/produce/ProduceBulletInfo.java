package com.bubbletrouble.game.packets.produce;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.bullet.Bullet;

public class ProduceBulletInfo extends ProduceInfo
{
	public float mouseX;
	public float mouseY;
	public float x;
	public float y;

	@Override
	public GameObject produce(State linkedState)
	{
		Bullet bullet = new Bullet(linkedState, new Vector2(mouseX, mouseY), new Vector2(x, y));
		bullet.setId(id);
		return bullet;
	}

}
