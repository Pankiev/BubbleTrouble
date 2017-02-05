package com.bubbletrouble.game.server.packets.action;

import com.bubbletrouble.game.libgdxcommon.GameObject;

public interface Action
{
	void makeAction(GameObject gameObject);

	void applyChangesToOther(GameObject gameObject);

	// boolean anythingChanged();
}