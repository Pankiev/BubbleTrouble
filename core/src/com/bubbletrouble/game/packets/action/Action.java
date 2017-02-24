package com.bubbletrouble.game.packets.action;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;

public interface Action
{
	void makeAction(GameObject gameObject);

	void applyChangesToOther(GameObject gameObject);

	// boolean anythingChanged();
}
