package com.bubbletrouble.game.server.packets.action;

import com.bubbletrouble.game.server.packets.Info;
import com.bubbletrouble.game.server.packets.Registerable;

@Registerable
public class CollisionActionInfo extends Info
{
	public long targetId = -1;
	public CollisionAction action = null;
}