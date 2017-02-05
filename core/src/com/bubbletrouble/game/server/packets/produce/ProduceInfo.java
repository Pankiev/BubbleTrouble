package com.bubbletrouble.game.server.packets.produce;

import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.server.packets.Info;

public abstract class ProduceInfo extends Info
{
	public long id;
	public abstract GameObject produce();
}
