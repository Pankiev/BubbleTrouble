package com.bubbletrouble.game.states.play;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.server.packets.ProduceInfo;

public abstract class PlayState extends State
{
	Map<Long, GameObject> gameObjects = new TreeMap<Long, GameObject>();

	@Override
	public void render(SpriteBatch batch)
	{
		for (GameObject object : gameObjects.values())
			object.render(batch);
	}

	@Override
	public void update()
	{
		for (GameObject object : gameObjects.values())
			object.update();
	}

	public void removeObject(long id)
	{
		gameObjects.remove(id);
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
		GameObject object = info.produce();
		gameObjects.put(info.id, object);
	}

	public void addObjects(ProduceInfo[] infos)
	{
		for (ProduceInfo produceInfo : infos)
			addObject(produceInfo);
	}


	public ProduceInfo[] getGameObjects()
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
}
