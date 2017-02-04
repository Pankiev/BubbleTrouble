package com.bubbletrouble.game.server.packets;

@Registerable
public class ActionInfo extends Info
{
	public long targetId = -1;
	public Action action = null;
}
