package com.bubbletrouble.game.server.packets;

@Registerable
public class CollisionActionInfo extends Info
{
	public long targetId = -1;
	public CollisionAction action = null;
}