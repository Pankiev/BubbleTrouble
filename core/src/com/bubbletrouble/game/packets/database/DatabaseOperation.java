package com.bubbletrouble.game.packets.database;

import com.bubbletrouble.game.kryonetcommon.Registerable;

@Registerable
@FunctionalInterface
public interface DatabaseOperation
{
	void perform();
}
