package com.bubbletrouble.game.libgdxcommon.communication.action;

import com.bubbletrouble.game.libgdxcommon.GameObject;

public interface Action
{
	public static final Action NoAction = (gameObject) -> {};

	void makeAction(GameObject gameObject);
}
