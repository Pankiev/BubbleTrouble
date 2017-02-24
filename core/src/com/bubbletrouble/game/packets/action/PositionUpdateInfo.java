package com.bubbletrouble.game.packets.action;

import com.bubbletrouble.game.kryonetcommon.Info;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;

public class PositionUpdateInfo extends Info
{
	public long id;
	public float x;
	public float y;

	public void update(GameObject object)
	{
		object.setPosition(x, y);
	}
}
