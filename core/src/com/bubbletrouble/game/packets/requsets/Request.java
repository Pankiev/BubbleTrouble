package com.bubbletrouble.game.packets.requsets;

import com.bubbletrouble.game.states.play.PlayState;

@FunctionalInterface
public interface Request
{
	void perform(PlayState state);
}
