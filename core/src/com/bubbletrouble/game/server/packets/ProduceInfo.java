package com.bubbletrouble.game.server.packets;

import com.bubbletrouble.game.libgdxcommon.GameObject;

public abstract class ProduceInfo extends Info
{
	public long id;
	public abstract GameObject produce();
}
