package com.bubbletrouble.game.server.packets;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.GameObject;

public interface CollisionAction
{
	void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision);

	void applyChangesToOther(GameObject gameObject);
}
