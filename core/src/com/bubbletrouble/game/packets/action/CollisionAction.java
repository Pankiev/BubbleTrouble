package com.bubbletrouble.game.packets.action;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;

public interface CollisionAction
{
	void makeAction(GameObject gameObject, Collection<GameObject> possibleCollision);

	void applyChangesToOther(GameObject gameObject);
}
