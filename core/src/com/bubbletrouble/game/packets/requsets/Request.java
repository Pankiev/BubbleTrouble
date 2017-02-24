package com.bubbletrouble.game.packets.requsets;

import com.bubbletrouble.game.states.play.PlayState;

public interface Request
{
	// boolean confitionsFulfilled(GameObject object);

	void perform(PlayState state);
}
