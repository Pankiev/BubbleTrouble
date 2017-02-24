package com.bubbletrouble.game.states.play;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.server.packets.produce.ProduceInfo;



public abstract class PlayState extends State
{
	Map<Long, GameObject> gameObjects = Collections.synchronizedMap(new TreeMap<Long, GameObject>());
	private List<GameObject> garbage = new ArrayList<GameObject>();

	@Override
	public void render(SpriteBatch batch)
	{
		final SpriteBatch batch2 = batch;
		gameObjects.forEach(new BiConsumer<Long, GameObject>()
		{
			@Override
			public void accept(Long id, GameObject object)
			{
				object.render(batch2);
			}
		});
		// for (GameObject object : gameObjects.values())
		// object.render(batch);
	}

	@Override
	public void update()
	{
		for (GameObject object : gameObjects.values())
			object.update();
		clearGarbage();
	}

	public void removeObject(long id)
	{
		GameObject toRemove = gameObjects.get(id);
		removeObject(toRemove);
	}

	public void removeObject(GameObject object)
	{
		gameObjects.remove(object.getId());
	}

	public GameObject getObject(long id)
	{
		return gameObjects.get(id);
	}

	public void addObject(GameObject gameObject, long id)
	{
		gameObjects.put(id, gameObject);
	}

	public void addObject(ProduceInfo info)
	{
		GameObject object = info.produce(this);
		gameObjects.put(info.id, object);
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

	public Collection<GameObject> getGameObjects()
	{
		return gameObjects.values();
	}

	protected void clearGarbage()
	{
		while (!(garbage.isEmpty()))
		{
			GameObject trash = garbage.remove(0);
			removeObject(trash);
		}
	}

	public void addToGarbage(GameObject trash)
	{
		garbage.add(trash);
	}

}
