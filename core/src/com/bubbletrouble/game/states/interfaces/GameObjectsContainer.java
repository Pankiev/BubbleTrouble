package com.bubbletrouble.game.states.interfaces;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;

public interface GameObjectsContainer
{

	GameObject removeObject(long id);

	GameObject removeObject(GameObject object);

	GameObject getObject(long id);

	void addObject(GameObject gameObject, long id);

	Collection<GameObject> getGameObjects();

	void addToGarbage(GameObject trash);

}