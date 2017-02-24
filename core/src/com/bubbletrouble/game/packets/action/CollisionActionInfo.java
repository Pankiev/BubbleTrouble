package com.bubbletrouble.game.packets.action;

import com.bubbletrouble.game.kryonetcommon.Info;
import com.bubbletrouble.game.kryonetcommon.Registerable;

@Registerable
public class CollisionActionInfo extends Info
{
	public long targetId = -1;
	public CollisionAction action = null;
}