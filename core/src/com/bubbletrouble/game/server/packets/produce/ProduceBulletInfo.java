package com.bubbletrouble.game.server.packets.produce;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Bullet;

public class ProduceBulletInfo extends ProduceInfo
{
	public float mouseX;
	public float mouseY;
	public float x;
	public float y;

	@Override
	public GameObject produce()
	{
		Bullet bullet = new Bullet(new Vector2(mouseX, mouseY), new Vector2(x, y));
		bullet.setId(id);
		return bullet;
	}

}
