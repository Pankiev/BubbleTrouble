package com.bubbletrouble.game.states.play;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.interfaces.GameObjectsContainer;

public abstract class PlayState extends State implements GameObjectsContainer
{
	protected Map<Long, GameObject> gameObjects = Collections.synchronizedMap(new TreeMap<Long, GameObject>());
	private List<GameObject> garbage = new ArrayList<GameObject>();
	private Map<Long, GameObject> newObjects = new TreeMap<Long, GameObject>();

	@Override
	public void render(SpriteBatch batch)
	{
		batch.setProjectionMatrix(camera.combined);
		gameObjects.forEach((id, object) -> object.render(batch));
	}

	@Override
	public GameObject removeObject(long id)
	{
		gameObjects.forEach((ID, object) -> System.out.println(object.getClass().getSimpleName() + " " + ID));
		GameObject toRemove = gameObjects.get(id);
		return removeObject(toRemove);
	}

	@Override
	public GameObject removeObject(GameObject object)
	{
		return gameObjects.remove(object.getId());
	}

	@Override
	public GameObject getObject(long id)
	{
		return gameObjects.get(id);
	}

	@Override
	public void addObject(GameObject gameObject, long id)
	{
		newObjects.put(id, gameObject);
	}

	public void addObject(ProduceInfo info)
	{
		GameObject object = info.produce(this);
		newObjects.put(info.id, object);
	}

	public void addObjects(ProduceInfo[] infos)
	{
		for (ProduceInfo produceInfo : infos)
			addObject(produceInfo);
	}

	public ProduceInfo[] getGameObjectsInfo()
	{
		ProduceInfo[] info = new ProduceInfo[gameObjects.size()];
		int i = 0;
		for (GameObject gameObject : gameObjects.values())
		{
			info[i] = gameObject.produceInfo();
			i++;
		}
		return info;
	}

	@Override
	public Collection<GameObject> getGameObjects()
	{
		return gameObjects.values();
	}

	protected void clearGarbage()
	{
		garbage.forEach((trash) -> removeObject(trash));
		garbage.clear();
	}

	public void addToGarbage(GameObject trash)
	{
		garbage.add(trash);
	}

	public boolean hasObject(long id)
	{
		return gameObjects.containsKey(id);
	}

	protected void pushNewObjects()
	{
		newObjects.forEach((id, object) -> gameObjects.put(id, object));
		newObjects.clear();
	}

}
