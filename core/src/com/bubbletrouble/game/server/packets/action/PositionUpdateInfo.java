package com.bubbletrouble.game.server.packets.action;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.Info;

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
