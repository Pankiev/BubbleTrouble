package com.bubbletrouble.game.states.play;

import java.util.Collection;

import com.bubbletrouble.game.libgdxcommon.objects.GameObject;

public interface GameObjectsContainer
{

	void removeObject(long id);

	void removeObject(GameObject object);

	GameObject getObject(long id);

	void addObject(GameObject gameObject, long id);

	Collection<GameObject> getGameObjects();

	void addToGarbage(GameObject trash);

}