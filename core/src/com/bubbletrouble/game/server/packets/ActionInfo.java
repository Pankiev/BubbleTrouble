package com.bubbletrouble.game.server.packets;

import com.bubbletrouble.game.libgdxcommon.communication.action.Action;

@Registerable
public class ActionInfo
{
	public int targetId = -1;
	public Action action = Action.NoAction;
}
