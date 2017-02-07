package com.bubbletrouble.game.server.packets.requsets;

import com.bubbletrouble.game.server.packets.Registerable;

@Registerable
public class ShootRequest
{
	public long id;
	public float mouseX;
	public float mouseY;
}
