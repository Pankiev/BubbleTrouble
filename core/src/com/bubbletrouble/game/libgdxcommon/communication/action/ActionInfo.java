package com.bubbletrouble.game.libgdxcommon.communication.action;

import com.bubbletrouble.game.server.packets.Registerable;

@Registerable
public class ActionInfo
{
	public int targetId = -1;
	public Action action = Action.NoAction;
}
